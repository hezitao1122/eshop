package com.eshop.inventory.hystrixy.collapser;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.feign.ItemDescFeign;
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
 * @description: 商品详情批量操作的command
 * -----------------------------------
 * @title: ItemDescCollasperCommand
 * @projectName eshop
 * @date 2020/5/18 22:11
 */
public class ItemDesCollasperCommand extends HystrixCollapser<List<TbItemDescDTO>, TbItemDescDTO, Long> {


    private Long itemDescId;

    private ItemDescFeign feign;

    public ItemDesCollasperCommand(Long itemDescId, ItemDescFeign feign) {
        this.itemDescId = itemDescId;
        this.feign = feign;
    }


    /**
     * description: 返回请求参数的方法
     *
     * @return: java.lang.Long
     * @Author: zeryts
     * @email: hezitao@agree.com
     * @Date: 2020/5/18 22:13
     */
    @Override
    public Long getRequestArgument() {
        return itemDescId;
    }

    /**
     * description: 初始化一个批量查询的请求command
     *
     * @param collection
     * @return: com.netflix.hystrix.HystrixCommand<java.util.List < com.eshop.inventory.manage.item.dto.TbItemDescDTO>>
     * @Author: zeryts
     * @email: hezitao@agree.com
     * @Date: 2020/5/18 22:14
     */
    @Override
    protected HystrixCommand<List<TbItemDescDTO>> createCommand(Collection<CollapsedRequest<TbItemDescDTO, Long>> collection) {
        return new ItemDesCollasperCommand.BatchCommand(collection, feign);
    }

    @Override
    protected void mapResponseToRequests(List<TbItemDescDTO> tbItemDescDTOS, Collection<CollapsedRequest<TbItemDescDTO, Long>> collection) {
        Map<Long, TbItemDescDTO> collect = tbItemDescDTOS.stream().collect(Collectors.toMap(TbItemDescDTO::getItemId, dto -> dto));

        for (CollapsedRequest<TbItemDescDTO, Long> coll : collection) {
            coll.setResponse(collect.get(coll.getArgument()));
        }
    }

    private static final class BatchCommand extends HystrixCommand<List<TbItemDescDTO>> {
        public final Collection<CollapsedRequest<TbItemDescDTO, Long>> requests;

        public final ItemDescFeign feign;

        /**
         * 功能描述: 批量查询的结果拼接command<br>
         * 〈〉
         *
         * @param requests 传入的需要拼接的参数
         * @param feign    item操作的Feign
         * @since: 1.0.0
         * @Author: zeryts
         * @Date: 2020/5/17 16:19
         */
        public BatchCommand(Collection<CollapsedRequest<TbItemDescDTO, Long>> requests, ItemDescFeign feign) {

            super(Setter.withGroupKey(
                    HystrixCommandGroupKey.Factory.asKey("ItemDescCachePerwarmCommandGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("TbItemDescCollapserBatchCommand"))
            );
            this.requests = requests;
            this.feign = feign;
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
        protected List<TbItemDescDTO> run() throws Exception {
            List<Long> ids = requests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList());
            ResultDto<TbItemDescDTO> byIds = feign.findByIds(ids);
            return byIds.getDataLis();
        }
    }

}
