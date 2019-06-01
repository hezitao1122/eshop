package com.eshopinventory.inventory.config.thread;

import com.eshopinventory.inventory.manage.item.request.Request;
import com.eshopinventory.inventory.manage.item.request.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zeryts
 * @description: 请求处理线程池：单例
 * ```````````````````````````
 * @title: RequestProcessorThreadPool
 * @projectName inventory
 * @date 2019/5/31 22:38
 */
public class RequestProcessorThreadPool {
    // 在实际项目中，你设置线程池大小是多少，每个线程监控的那个内存队列的大小是多少
    // 都可以做到一个外部的配置文件中
    // 我们这了就给简化了，直接写死了，好吧

    /**
     * 线程池
     */
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);

    /**
     * 初始化线程池的方法
     */
    public RequestProcessorThreadPool() {
        RequestQueue requestQueue = RequestQueue.getInstance();
        for(int i = 0; i < 10; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(100);
            requestQueue.addRequestQueue(queue);
            threadPool.submit(new RequestProcessorThread(queue));
        }
    }
    /**
     * 匿名内部类进行单例
     */
    private static class Singleton{

        private static RequestProcessorThreadPool install ;

        static {
            install = new RequestProcessorThreadPool();
        }
        public static RequestProcessorThreadPool getInstance() {
            return install;
        }
    }
    /**
     * 功能描述: 获取单例的线程池<br>
     * 〈〉
     * @return: com.eshopinventory.inventory.config.thread.RequestProcessorThreadPool
     * @since: 1.0.0
     * @Author: Administrator
     * @Date: 2019/5/31 22:46
     */
    public static RequestProcessorThreadPool getInstance(){

        return Singleton.getInstance();
    }
    /**
     * 初始化的便捷方法
     */
    public static void init() {
        getInstance();
    }
}
