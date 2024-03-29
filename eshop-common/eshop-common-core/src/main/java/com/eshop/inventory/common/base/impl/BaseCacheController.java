package com.eshop.inventory.common.base.impl;

import com.eshop.inventory.common.base.BaseFeign;
import com.eshop.inventory.common.base.IBaseCacheController;
import com.eshop.inventory.common.base.IBaseEhCacheController;
import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.queue.RebuildCacheQueue;
import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author zeryts
 * @description: 用于reids缓存操作的公共接口
 * ```````````````````````````
 * @title: IBaseEhCacheController
 * @projectName inventory
 * @date 2019/6/20 23:16
 */
@Slf4j
public abstract class BaseCacheController<T extends BaseDTO, ID extends Serializable> implements IBaseEhCacheController<T, ID>, IBaseCacheController<T, ID> {

    @Override
    @RequestMapping(value = "/findEhcache")
    public ResultDto<T> getEhcache(ID id) {
        return new ResultDto<>(getCacheService().getLoadEhCache(id));
    }

    @Override
    @RequestMapping(value = "/delEhcache")
    public ResultDto<T> deleteEhcache(ID id) {
        getCacheService().delLoadEhCache(id);
        return new ResultDto<>();
    }


    @Override
    @RequestMapping(value = "/get")
    public ResultDto<T> getCache(ID id) {
        T t = null;
        //先从redis中取，取出来的话就直接返回
        t = getCacheService().getLoadCache(id);

        //取不出来去ehcache中取
        if (t == null) {
            t = getCacheService().getLoadEhCache(id);
        }

        /**
         * 如果缓存中取不出来,则需要被动重建数据
         * 1.直接去对应的服务器数据库中取
         * 2.返回给nginx
         * 3.同时推送一条消息到内存队列中
         * 4.后台异步去执行更新缓存
         */
        if (t == null) {
            HystrixCommand<T> hystrixyCommand = getHystrixyCommand(id);
            if (hystrixyCommand == null) {
                //如果HystrixCommand为空，直接调用
                ResultDto<T> tResultDto = getFeign().find(id);
                t = tResultDto.getData();
            } else {
                //如果HystrixCommand不为空,使用Hystrix调用
                /**
                 * execute 代表同步执行
                 */

                t = hystrixyCommand.execute();

                /**
                 * 异步执行
                 */
                /*
                    Future<T> queue = hystrixyCommand.queue();
                    try {
                        Thread.sleep(1000);
                        T t1 = queue.get();
                    } catch (Exception e) {
                        log.info(e.toString(), e);
                    }
                 */
            }
            RebuildCacheQueue.getInstance().add(t);
        }
        if(t == null){
            getCacheService().saveLoadCache(id,null);

        }
        return new ResultDto<T>(t);
    }


    @Override
    @RequestMapping(value = "/del")
    public ResultDto<T> deleteCache(ID id) {
        return new ResultDto(getCacheService().deleteLoadCache(id));
    }

    @Override
    public ResultDto<List<T>> getBatchCache(List<ID> ids) {

        // TODO  方案一
//
//        HystrixObservableCommand<T> command = getHystrixObservableCommand(ids);
//        /**
//         *  订阅一个从依赖请求中响应的Observable对象，拿到后就执行了
//         */
//        Observable<T> observe = command.observe();
//        /**
//         *  延迟订阅,订阅一个从依赖请求中响应的Observable对象， 在subscribe方法执行的时候才去执行
//         */
////        Observable<T> observe1 = command.toObservable();
//
//        ResultDto<List<T>> dto = new ResultDto<List<T>>();
//        List<T> list = new ArrayList<>();
//        dto.setData(list);
//        observe.subscribe(new Observer<T>() {
//            @Override
//            public void onCompleted() {
//                /**
//                 * 代表执行完成后调用的方法
//                 */
//                log.info("执行完成了！");
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                /**
//                 * 抛错的情况调用此方法
//                 */
//                log.info(e.toString(), e);
//            }
//
//            @Override
//            public void onNext(T t) {
//                // on next里面执行的方法的返回
//                log.info("执行的返回数据为：[result -> {}]", t);
//                list.add(t);
//            }
//        });
//        return dto;

        //TODO 方案二
//        List<T> list = new ArrayList<>();
//        for (ID id:ids) {
//            HystrixCommand<T> command = getHystrixyCommand(id);
//            T t = command.execute();
//
//            //是否走的是缓存
//            boolean isCache = command.isResponseFromCache();
//            log.info("isCache ? [res->{}]" , isCache);
//            list.add(t);
//        }
//        return list

        //TODO 方案三
        List<Future<T>> futures = new ArrayList<>();
        for (ID id : ids) {
            HystrixCollapser command = getHystrixCollapser(id);
            futures.add(command.queue());
        }
        final List<T> list = new ArrayList<>();
        futures.forEach(f -> {
            try {
                T dto = f.get();
                list.add(dto);
            } catch (InterruptedException e) {
                log.info(e.toString(), e);
            } catch (ExecutionException e) {
                log.info(e.toString(), e);
            }

        });
        return new ResultDto<List<T>>(list);
    }

    /**
     * 功能描述: 抽象的Feign<br>
     * 〈〉
     *
     * @return: com.eshop.inventory.common.base.BaseFeign<T, ID>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:42
     */
    abstract public BaseFeign<T, ID> getFeign();

    /**
     * 功能描述: 高可用Hystrix根据ID查询的HystrixCommand<br>
     * 〈〉
     *
     * @param id 查询的数据ID
     * @return: com.netflix.hystrix.HystrixCommand<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:41
     */
    abstract public HystrixCommand<T> getHystrixyCommand(ID id);

    /**
     * 功能描述: 高可用Hystrix下批量查询的HystrixObservableCommand<br>
     * 〈〉
     *
     * @param ids 批量查询的id
     * @return: com.netflix.hystrix.HystrixObservableCommand<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:40
     */
    abstract public HystrixObservableCommand<T> getHystrixObservableCommand(List<ID> ids);

    /**
     * 功能描述: 高可用Hystrix下批量查询的HystrixCollapser带请求拼接功能<br>
     * 〈〉
     *
     * @param id 批量查询的id
     * @return: com.netflix.hystrix.HystrixObservableCommand<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/14 21:40
     */
    abstract public HystrixCollapser<List<T>, T, ID> getHystrixCollapser(ID id);
}
