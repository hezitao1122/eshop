package com.eshop.inventory.hystrixy.collapser;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.feign.ItemFeign;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zeryts
 * @description: 通过Hystrix请求合并技术进行请求合并的功能 . 使用HystrixCommand + request cache功能
 * 一个批量过来,依然使用多个command去执行,相同的商品查询一次,不同的商品查询多次
 * 有三种级别
 * 1. Global context : 即 Tomcat的所有线程,对于一个依赖服务的任何一个Command调用都会进行请求合并
 * 2. User request context : 即 Tomcat的一个请求下的所有Command都能合并
 * 3. Object modeling 基于对象的请求调用
 * ```````````````````````````
 * @title: ItemCollapserCommand
 * @projectName inventory
 * @date 2020/5/17 15:50
 */
public class ItemCollapserCommand extends HystrixCollapser<List<TbItemDTO>, TbItemDTO, Long> {

    private Long itemId;

    private ItemFeign itemFeign;

    public ItemCollapserCommand(Long itemId, ItemFeign itemFeign) {
        this.itemId = itemId;
        this.itemFeign = itemFeign;

    }

    /**
     * 功能描述: 返回请求参数的方法<br>
     * 〈〉
     *
     * @return: java.lang.Long
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/17 16:00
     */
    @Override
    public Long getRequestArgument() {
        return itemId;
    }

    /**
     * 功能描述: 初始化一个批量查询的请求Command<br>
     * 〈〉
     *
     * @param collapsedRequests 请求参数
     * @return: com.netflix.hystrix.HystrixCommand<java.util.List < com.eshop.inventory.manage.item.dto.TbItemDTO>>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/17 16:34
     */
    @Override
    protected HystrixCommand<List<TbItemDTO>> createCommand(Collection<CollapsedRequest<TbItemDTO, Long>> collapsedRequests) {

        return new BatchCommand(collapsedRequests, itemFeign);
    }

    /**
     * 功能描述: 使用缓存<br>
     * 〈〉
     *
     * @param
     * @return: java.lang.String
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/17 16:34
     */
    @Override
    protected String getCacheKey() {

        return "item_cache_" + itemId;
    }

    /**
     * 功能描述: 将请求结果与相应结果进行拼接对应的方法<br>
     * 〈〉
     *
     * @param batchResponse     相应结果
     * @param collapsedRequests 请求结果
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/17 16:25
     */
    @Override
    protected void mapResponseToRequests(List<TbItemDTO> batchResponse,
                                         Collection<CollapsedRequest<TbItemDTO, Long>> collapsedRequests) {

        Map<Long, TbItemDTO> collect = batchResponse.stream().collect(Collectors.toMap(TbItemDTO::getId, dto -> dto));

        for (CollapsedRequest<TbItemDTO, Long> coll : collapsedRequests) {
            coll.setResponse(collect.get(coll.getArgument()));
        }
    }

    /**
     * @author zeryts
     * @description: 请求拼接操作的类
     * ```````````````````````````
     * @title: BatchCommand
     * @projectName inventory
     * @date 2020/5/17 15:50
     */
    private static final class BatchCommand extends HystrixCommand<List<TbItemDTO>> {


        public final Collection<CollapsedRequest<TbItemDTO, Long>> requests;

        public final ItemFeign itemFeign;

        /**
         * 功能描述: 批量查询的结果拼接command<br>
         * 〈〉
         *
         * @param requests  传入的需要拼接的参数
         * @param itemFeign item操作的Feign
         * @since: 1.0.0
         * @Author: zeryts
         * @Date: 2020/5/17 16:19
         */
        public BatchCommand(Collection<CollapsedRequest<TbItemDTO, Long>> requests, ItemFeign itemFeign) {

            super(Setter.withGroupKey(
                    HystrixCommandGroupKey.Factory.asKey("ItemCachePerwarmCommandGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("TbItemCollapserBatchCommand"))
            );
            this.requests = requests;
            this.itemFeign = itemFeign;
        }

        /**
         * 功能描述: 执行拼接命令<br>
         *
         * @return: java.util.List<com.eshop.inventory.manage.item.dto.TbItemDTO>
         * @since: 1.0.0
         * @Author: zeryts
         * @Date: 2020/5/17 16:20
         */
        @Override
        protected List<TbItemDTO> run() throws Exception {

            List<Long> ids = requests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList());
            ResultDto<TbItemDTO> byIds = itemFeign.findByIds(ids);
            return byIds.getDataLis();
        }
    }

}
