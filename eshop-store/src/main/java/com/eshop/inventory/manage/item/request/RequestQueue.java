package com.eshop.inventory.manage.item.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zeryts
 * @description: 请求内存队列
 * ```````````````````````````
 * @title: RequestQueue
 * @projectName inventory
 * @date 2019/5/30 23:03
 */
public class RequestQueue<ID> {
    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<ArrayBlockingQueue<Request>>();
    /**
     * 标识位map
     */
    private Map<ID, Boolean> cacheMap = new ConcurrentHashMap<>();

    /**
     * 单例有很多种方式去实现：我采取绝对线程安全的一种方式
     * <p>
     * 静态内部类的方式，去初始化单例
     * <p>
     * 内部类的初始化，一定只会发生一次，不管多少个线程并发去初始化
     */
    private static class Singleton {

        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }

    }

    /**
     * 功能描述: 获取单例的请求内存队列<br>
     *
     * @return: com.eshop.inventory.manage.item.request.RequestQueue
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:14
     */
    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }

    /**
     * 功能描述: 添加一个内存队列<br>
     * 〈〉
     *
     * @param queue 内存队列
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/31 22:19
     */
    public void addRequestQueue(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

    /**
     * 功能描述: 获取内存队列的数量<br>
     *
     * @return: int
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:14
     */
    public int getSize() {
        return queues.size();
    }

    /**
     * 功能描述: 根据hash的路由路径获取对应的内存队列<br>
     * 〈〉
     *
     * @param index hash后的主键
     * @return: java.util.concurrent.ArrayBlockingQueue<com.eshop.inventory.manage.item.request.Request>
     * @since: 1.0.0
     * @author zeryts
     * @Date: 2019/5/30 23:17
     */
    public ArrayBlockingQueue<Request> getQueue(int index) {
        return queues.get(index);
    }

    public Map<ID, Boolean> getCacheMap() {
        return cacheMap;
    }
}
