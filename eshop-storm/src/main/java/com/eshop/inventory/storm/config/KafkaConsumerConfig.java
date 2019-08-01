package com.eshop.inventory.storm.config;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.Properties;

/**
 * @author zeryts
 * @description: Kafka的初始化配置类
 * ```````````````````````````
 * @title: KafkaConsumerConfig
 * @projectName inventory
 * @date 2019/8/1 23:24
 */
public class KafkaConsumerConfig {
    private static ConsumerConfig consumerConfig;

    private static ConsumerConnector consumerConnector;

    public static String TOPIC = KafkaConstant.TOPIC;

    /**
     * 功能描述: kafka初始化的配置类<br>
     * 〈〉
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/8/1 23:27
     */
    public static void init(){
        Properties props = new Properties();
        //指定集群连接地址
        props.put("zookeeper.connect", KafkaConstant.ZOOKEEPER_CONNECT);
        //指定groupid消费者id
        props.put("group.id",KafkaConstant.GROUP_ID );
        //session过期时间
        props.put("zookeeper.session.timeout.ms",KafkaConstant.ZOOKEEPER_SESSION_TIMEOUT );
        //等待时间
        props.put("zookeeper.sync.time.ms", KafkaConstant.ZOOKEEPER_SYNC_TIME);
        //提交超时时间
        props.put("auto.commit.interval.ms",KafkaConstant.AUTO_COMMIT_INTERVAL );
        consumerConfig = new ConsumerConfig(props);
        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);
    }
    /**
     * 功能描述: 获取kafka的配置类<br>
     * 〈〉
     * @return: kafka.consumer.ConsumerConfig kafka的配置类·
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/8/1 23:36
     */
    public static ConsumerConfig getConsumerConfig() {
        return consumerConfig;
    }
    /**
     * 功能描述: 获取kafka的消费者对象<br>
     * 〈〉
     *
     * @param
     * @return: kafka.javaapi.consumer.ConsumerConnector
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/8/1 23:46
     */
    public static ConsumerConnector getConsumerConnector() {

        return consumerConnector;
    }
}
