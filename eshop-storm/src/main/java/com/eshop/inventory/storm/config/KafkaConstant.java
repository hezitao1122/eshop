package com.eshop.inventory.storm.config;

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


}
