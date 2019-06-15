package com.eshop.inventory.config.kafka;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author zeryts
 * @description: Kafka的启动的消费者
 * ```````````````````````````
 * @title: KafkaConsumer
 * @projectName inventory
 * @date 2019/6/15 15:25
 */
public class KafkaConsumer implements Runnable{

    /**
     * 定义kafka的consumer对象
     */
    private final ConsumerConnector consumerConnector;
    /**
     * 定义坚挺的topic
     */
    private final String topic;

    /**
     * 功能描述: 初始化构造函数，传入一个需要消费的topic<br>
     * 〈〉
     * @param topic 需要消费的topic
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 15:29
     */
    public KafkaConsumer(String topic) {
        consumerConnector = Consumer.createJavaConsumerConnector(createConsumerConfig());
        this.topic = topic;
    }


    @Override
    public void run() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        //消费的topic,以及打算用几个线程去消费topic
        topicCountMap.put(topic, 1);
        //这里代表每个topic会对应多个kafka的stream,代表每个topic传入几个数字,其就会给你几个stream
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        for (KafkaStream stream : streams) {

            new Thread(new KafkaMessageProcessor(stream)).start();
        }
    }


    /**
     * 功能描述: 初始化kafka的consumer配置<br>
     * 〈〉
     * @return: kafka.consumer.ConsumerConfig
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 15:30
     */
    private static ConsumerConfig createConsumerConfig() {
        Properties props = new Properties();
        //指定集群连接地址
        props.put("zookeeper.connect", "192.168.31.226:2181,192.168.31.225:2181,192.168.31.224:2181");
        //指定groupid消费者id
        props.put("group.id", "eshop-cache-group");
        //session过期时间
        props.put("zookeeper.session.timeout.ms", "400");
        //等待时间
        props.put("zookeeper.sync.time.ms", "200");
        //提交超时时间
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }
}
