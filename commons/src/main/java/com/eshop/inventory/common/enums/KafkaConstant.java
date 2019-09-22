package com.eshop.inventory.common.enums;

/**
 * @author zeryts
 * @description: Kafka的配置常量类
 * ```````````````````````````
 * @title: KafkaConstant
 * @projectName inventory
 * @date 2019/8/1 23:31
 */
public class KafkaConstant {
    /**
     * 指定ZK集群连接地址
     */
    public static final String ZOOKEEPER_CONNECT = "192.168.31.226:2181,192.168.31.225:2181,192.168.31.224:2181";
    /**
     * 指定groupid消费者id
     */
    public static final String GROUP_ID = "eshop-cache-group";
    /**
     * session过期时间
     */
    public static final String ZOOKEEPER_SESSION_TIMEOUT = "40000";
    /**
     * 等待时间
     */
    public static final String ZOOKEEPER_SYNC_TIME = "200";
    /**
     * 提交超时时间
     */
    public final static String AUTO_COMMIT_INTERVAL = "1000";
    /**
     * 监听的topic
     */
    public final static String TOPIC = "access-log";
    /**
     * message
     */
    public final static String MESSAGE = "message";
    /**
     * id
     */
    public final static String ID = "id";

    /**
     * 用于获取task-id任务的分布式锁
     */
    public static final String TASK_ID_LIST = "task-id-list";
    /**
     * 存储数据的节点
     */
    public static final String TASK_HOT = "task-hot-item-list-";

    /**
     * 用于获取task-id任务的分布式锁
     */
    public static final String TASK_ID_LOCK = "task-id-lock";
    /**
     * 预热状态检查锁
     */
    public static final String TASK_ID_STATUS_LOCK = "task-id-status-lock-";
    /**
     * 状态存储的path
      */
    public static final String TASK_ID_STATUS = "task-id-status-";


}
