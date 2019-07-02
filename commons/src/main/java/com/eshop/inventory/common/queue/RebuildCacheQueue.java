package com.eshop.inventory.common.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zeryts
 * @description: 重建缓存的queue
 * ```````````````````````````
 * @title: RebuildCacheQueue
 * @projectName inventory
 * @date 2019/7/2 23:02
 */
@Slf4j
public class RebuildCacheQueue {

    private ArrayBlockingQueue<Object> blockingQueue = new ArrayBlockingQueue(1000);
    /**
     * 功能描述: 向内存队列中推送一条消息<br>
     * 〈〉
     * @param t
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/2 23:11
     */
    public void add(Object t){
        try {
            blockingQueue.put(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能描述: 向内存队列中获取一条消息<br>
     * 〈〉
     * @return: T
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/2 23:11
     */
    public Object take(){
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            log.info(e.toString(),e);
        }
        return null;
    }



    /**
     * 初始化实例的单例模式
     */
    private static class Singleton{
        private static RebuildCacheQueue instance;
        static {
            instance = new RebuildCacheQueue();
        }
        public static RebuildCacheQueue getInstance() {
            return instance;
        }
    }
    public static RebuildCacheQueue getInstance(){
        return Singleton.getInstance();
    }
}
