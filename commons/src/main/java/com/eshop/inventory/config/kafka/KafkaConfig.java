package com.eshop.inventory.config.kafka;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author zeryts
 * @description: Kafka操作的常量类
 * ```````````````````````````
 * @title: KafkaConfig
 * @projectName inventory
 * @date 2019/8/1 23:10
 */
//@Component
public class KafkaConfig {
    /**
     * 指定ZK集群连接地址
     */
    public static String ZOOKEEPER_CONNECT = "192.168.31.226:2181,192.168.31.225:2181,192.168.31.224:2181";
    /**
     * 指定groupid消费者id
     */
    public static String GROUP_ID = "eshop-cache-group";
    /**
     * session过期时间
     */
    public static String ZOOKEEPER_SESSION_TIMEOUT = "40000";
    /**
     * 等待时间
     */
    public static String ZOOKEEPER_SYNC_TIME = "200";
    /**
     * 提交超时时间
     */
    public static String AUTO_COMMIT_INTERVAL = "1000";

    @Value("${zookeeper.connect}")
    public void setZookeeperConnect(String zookeeperConnect) {
        ZOOKEEPER_CONNECT = zookeeperConnect;
    }
    @Value("#{zookeeper.group.id}")
    public void setGroupId(String groupId) {
        GROUP_ID = groupId;
    }
    @Value("#{zookeeper.session.timeout}")
    public void setZookeeperSessionTimeout(String zookeeperSessionTimeout) {
        ZOOKEEPER_SESSION_TIMEOUT = zookeeperSessionTimeout;
    }
    @Value("#{zookeeper.sync.time}")
    public void setZookeeperSyncTime(String zookeeperSyncTime) {
        ZOOKEEPER_SYNC_TIME = zookeeperSyncTime;
    }
    @Value("#{zookeeper.auto.commit.interval}")
    public void setAutoCommitInterval(String autoCommitInterval) {
        AUTO_COMMIT_INTERVAL = autoCommitInterval;
    }
}
