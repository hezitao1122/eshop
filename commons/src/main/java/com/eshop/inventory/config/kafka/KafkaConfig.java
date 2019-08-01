package com.eshop.inventory.config.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zeryts
 * @description: Kafka操作的常量类
 * ```````````````````````````
 * @title: KafkaConfig
 * @projectName inventory
 * @date 2019/8/1 23:10
 */
@Component
public class KafkaConfig {
    /**
     * 指定ZK集群连接地址
     */
    public static String ZOOKEEPER_CONNECT;
    /**
     * 指定groupid消费者id
     */
    public static String GROUP_ID;
    /**
     * session过期时间
     */
    public static String ZOOKEEPER_SESSION_TIMEOUT;
    /**
     * 等待时间
     */
    public static String ZOOKEEPER_SYNC_TIME;
    /**
     * 提交超时时间
     */
    public static String AUTO_COMMIT_INTERVAL;

    @Value("#{zookeeper.connect}")
    public static void setZookeeperConnect(String zookeeperConnect) {
        ZOOKEEPER_CONNECT = zookeeperConnect;
    }
    @Value("#{zookeeper.group.id}")
    public static void setGroupId(String groupId) {
        GROUP_ID = groupId;
    }
    @Value("#{zookeeper.session.timeout}")
    public static void setZookeeperSessionTimeout(String zookeeperSessionTimeout) {
        ZOOKEEPER_SESSION_TIMEOUT = zookeeperSessionTimeout;
    }
    @Value("#{zookeeper.sync.time}")
    public static void setZookeeperSyncTime(String zookeeperSyncTime) {
        ZOOKEEPER_SYNC_TIME = zookeeperSyncTime;
    }
    @Value("#{zookeeper.auto.commit.interval}")
    public static void setAutoCommitInterval(String autoCommitInterval) {
        AUTO_COMMIT_INTERVAL = autoCommitInterval;
    }
}
