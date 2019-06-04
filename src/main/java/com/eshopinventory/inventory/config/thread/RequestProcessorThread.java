package com.eshopinventory.inventory.config.thread;

import com.eshopinventory.inventory.common.base.BaseReaderRequest;
import com.eshopinventory.inventory.common.base.BaseWriterRequest;
import com.eshopinventory.inventory.common.entity.BaseEntity;
import com.eshopinventory.inventory.manage.item.request.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author zeryts
 * @description: 执行请求的工作线程
 * ```````````````````````````
 * @title: RequestProcessorThread
 * @projectName inventory
 * @date 2019/5/31 22:33
 */
@Slf4j
public class RequestProcessorThread<T extends BaseEntity, ID> implements Callable<Boolean> {

    /**
     * 自己监控的内存队列
     */
    private ArrayBlockingQueue<Request<T, ID>> queue;

    public RequestProcessorThread(ArrayBlockingQueue<Request<T, ID>> queue) {
        this.queue = queue;
    }


    @Override
    public Boolean call() throws Exception {
        try {
            while (true) {
                try {
                    //此方法，如果队列满了或者为空，则会阻塞执行
                    Request<T, ID> take = queue.take();
                    //查询是否为缓存强制刷新的请求
                    boolean forceRefresh = take.isForceRefresh();

                    if (!forceRefresh) {
                        //如果不是强制刷新缓存的请求，需要做请求去重处理
                        Map<ID, Boolean> cacheMap = take.getCacheMap();
                        //写请求标志位true ， 读请求标志为 false
                        if (take instanceof BaseWriterRequest) {
                            //如果是写请求，将设置为false
                            cacheMap.put(take.getId(), Boolean.TRUE);
                        } else if (take instanceof BaseReaderRequest) {
                            Boolean aBoolean = cacheMap.get(take.getId());
                            //如果为null
                            if (aBoolean == null) {
                                cacheMap.put(take.getId(), Boolean.FALSE);
                            }
                            //如果是读请求，并且前面也具有一个相同的写请求
                            if (aBoolean != null && !aBoolean)
                                continue;
                            //如果是读请求，前面没有相同读请求 , 将此读请求压入队列
                            if (aBoolean != null && aBoolean) {
                                cacheMap.put(take.getId(), Boolean.FALSE);
                            }
                        }
                    }


                    //如果能获取到，则代表线程能获取请求，执行
                    take.process();
                } catch (Exception e) {
                    log.info(e.toString(), e);
                }
            }
        } catch (Exception e) {
            log.info(e.toString(), e);
        }
        return null;
    }
}
