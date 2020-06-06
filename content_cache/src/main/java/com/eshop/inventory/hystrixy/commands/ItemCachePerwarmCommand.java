package com.eshop.inventory.hystrixy.commands;

import cn.hutool.db.DaoTemplate;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.feign.ItemFeign;
import com.eshop.inventory.manage.item.service.ItemCacheService;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author zeryts
 * @description: 用语查询多条数据的Command
 * HystrixCommand :是用来获取一条数据的Command
 * HystrixObservableCommand :是用来获取多条数据的Command
 * <p>
 * -----------------------------------
 * @title: ItemCachePerwarmCommand
 * @projectName inventory
 * @date 2020/5/10 16:47
 */
@Slf4j
public class ItemCachePerwarmCommand extends HystrixCommand<TbItemDTO> {

    private Long id;

    @Autowired
    private ItemFeign itemFeign;
    @Autowired
    private ItemCacheService service;


    public static HystrixCommandGroupKey GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("ItemCachePerwarmCommandGroup");
    public static HystrixCommandGroupKey TWO_GROUP_KEY = HystrixCommandGroupKey.Factory.asKey("TwoItemCachePerwarmCommandGroup");
    public static HystrixCommandKey COMMAND_KEY = HystrixCommandKey.Factory.asKey("ItemCachePerwarmCommand");

    /**
     * description: 初始化构造方法 ， 需要传入你在run使用的值，并且传入全局变量
     *
     * @param id item的ID
     * @Author: zeryts
     * @email: hezitao@agree.com
     * @Date: 2020/5/10 18:46
     */
    public ItemCachePerwarmCommand(Long id) {
        //绑定一个线程池 ， 默认为线程池模式
//        super(Setter.withGroupKey(GROUP_KEY)
//                .andCommandKey(COMMAND_KEY)
//        );

        super(
                /**
                 * GroupKey代表了一类底层服务 , 如果没有设置 threadpool的情况下,所有这一类服务使用同一个线程池
                 * 多个Command组成一组Group
                 */
                Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ItemCachePerwarmCommandGroup"))
                /**
                * 代表了一类Command , 即底层服务依赖的某一个接口 , 可以单独设置 threadpool 即每个接口使用不同的线程池
                */
                .andCommandKey(HystrixCommandKey.Factory.asKey("ItemCachePerwarmCommand"))
                 /**
                 *  代表了使用一类线程池pool , 同一个pool使用同一组线程池, 如果不进行设置,则默认同一个 groupkey使用同一组线程
                 */
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ItemCachePerwarmCommandPool"))
                        /**
                         * 这个参数用来设置线程池
                         */
                .andThreadPoolPropertiesDefaults(
                        /**
                         * 线程池使用这个Setter
                         */
                        HystrixThreadPoolProperties.Setter()
                            /**
                            * 默认为10
                            * 设置该线程池的大小
                            */
                            .withCoreSize(10)
                                /**
                                 * 动态扩容,可扩容到最大的线程池数量
                                 */
                            .withMaximumSize(30)
                                /**
                                 * 允许动态扩容的开关
                                 */
                            .withAllowMaximumSizeToDivergeFromCoreSize(true)
                                /**
                                 * 扩容后多久回收线程
                                 */
                            .withKeepAliveTimeMinutes(30)
                        /**
                        *   默认为5
                         *   1. 如果线程池满了,则其他线程会进入一个queue,等待线程释放
                         *   2.如果queue也满了,其他请求会进入reject,然后走fallback逻辑
                        */
                        .withQueueSizeRejectionThreshold(10)
                )
                        /**
                         * 此用于设置Command的一些通用设置
                         */
                .andCommandPropertiesDefaults(
                        /**
                         * 此为设置当隔离策略为信号量模式的情况下queue的最大大小,即允许访问的最大线程数,
                         * 超过此线程数会直接reject,并且进入fallback状态  仅仅对Sempaphore状态为有效的
                         */
                        HystrixCommandProperties.Setter().withExecutionIsolationSemaphoreMaxConcurrentRequests(10)
                ).andCommandPropertiesDefaults(
                   HystrixCommandProperties.Setter()
                           /**
                            * 配置经过断路器的流量,超过此阈值,才会进入断路器的下一步校验  default = 20
                            */
                        .withCircuitBreakerRequestVolumeThreshold(20)
                           /**
                            * 配置异常的比例  超过此比例则会进入断路器打开模式   default = 50
                            */
                        .withCircuitBreakerErrorThresholdPercentage(50)
                           /**
                            * 断路器打开的情况,经过多久，则会进入半开模式  default = 5000ms
                            */
                        .withCircuitBreakerSleepWindowInMilliseconds(5000)
                           /**
                            * 设置超时时长  default=1000ms  即 1S
                            */
                        .withExecutionTimeoutInMilliseconds(200)
                )
        );


        /**
         * 信号链模式 SEMAPHORE为信号量模式
         */
        /*
        super(Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("ItemCachePerwarmCommandGroup")).
                andCommandPropertiesDefaults( HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE))
        );
         */

        this.id = id;
    }


    @Override
    protected TbItemDTO run() throws Exception {
        return itemFeign.find(id).getData();
    }

    /**
     * 功能描述: 用于请求上下文缓存到内存的key<br>
     * 〈〉
     *
     * @return: java.lang.String
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/16 11:49
     */
    @Override
    protected String getCacheKey() {
        return "item_cache_" + id;
    }

    /**
     * 功能描述:  刷新内存缓存的方法, 使得下一次请求不会走上下文的请求<br>
     * 〈〉
     *
     * @param id 商品的ID
     * @return: boolean
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/16 12:18
     */
    public static boolean flush(Long id) {
        HystrixRequestCache.getInstance(COMMAND_KEY, HystrixConcurrencyStrategyDefault.getInstance()).
                clear("item_cache_" + id);
        return true;
    }

    /**
     * 功能描述:降级的逻辑代码 <br>
     * 〈〉
     *
     * @return: com.eshop.inventory.manage.item.dto.TbItemDTO
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/16 17:01
     */
    @Override
    protected TbItemDTO getFallback() {
        log.info("id为[{}]的商品走二次回调!",id);
        return new TwoItemCachePerwamCommand(id).execute();
    }
    /**
     * @author zeryts
     * @description: 二次降级的方法
     * <p>
     * -----------------------------------
     * @title: TwoItemCachePerwamCommand
     * @projectName inventory
     * @date 2020/5/10 16:47
     */
    private final class TwoItemCachePerwamCommand extends HystrixCommand<TbItemDTO>{

        private Long id;

        public TwoItemCachePerwamCommand(Long id){
            super(Setter.withGroupKey(TWO_GROUP_KEY)
                    .andCommandKey(COMMAND_KEY)
            );
            this.id = id;
        }


        @Override
        protected TbItemDTO run() throws Exception {
            return service.getLoadCache(id);
        }


        @Override
        protected TbItemDTO getFallback() {
            log.info("id为[{}]的商品二次回调也没查询到商品信息!",id);
            TbItemDTO dto = new TbItemDTO();
            TbItem item = new TbItem();
            item.setId(id);
            dto.setEntity(item);
            return dto;
        }
    }


}
