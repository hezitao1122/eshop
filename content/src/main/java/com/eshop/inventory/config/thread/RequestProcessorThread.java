package com.eshop.inventory.config.thread;

import com.eshop.inventory.common.entity.BaseEntity;
import com.eshop.inventory.manage.item.request.Request;
import lombok.extern.slf4j.Slf4j;

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
                //此方法，如果队列满了或者为空，则会阻塞执行
                Request<T, ID> take = queue.take();

                //如果能获取到，则代表线程能获取请求，执行
                take.process();
            }
        } catch (Exception e) {
            log.info(e.toString(), e);
        }
        return null;
    }
}
