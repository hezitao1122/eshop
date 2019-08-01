package com.eshop.inventory.storm.spout;

import com.eshop.inventory.storm.config.KafkaConsumerConfig;
import kafka.consumer.KafkaStream;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zeryts
 * @description: 消费kafka的Spout
 * ```````````````````````````
 * @title: AccessLogKafkaSpout
 * @projectName inventory
 * @date 2019/8/1 23:02
 */
public class AccessLogKafkaSpout extends BaseRichSpout {

    /**
     * 初始化时候传入进来的数据发射对象
     */
    private SpoutOutputCollector collector;

    /**
     * 功能描述: 对spout进行初始化的方法，此从kafka中获取需要的数据<br>
     * 〈〉
     *
     * @param conf      配置
     * @param context   在open方法初始化的时候会传入进来一个SpoutOutputCollector对象，然后这个对象用来发射数据出去的对象
     * @param collector 数据发射对象
     * @return: void
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/9 23:31
     */
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;

        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        //消费的topic,以及打算用几个线程去消费topic
        topicCountMap.put(KafkaConsumerConfig.TOPIC, 1);
        //这里代表每个topic会对应多个kafka的stream,代表每个topic传入几个数字,其就会给你几个stream
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = KafkaConsumerConfig.getConsumerConnector().createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(KafkaConsumerConfig.TOPIC);

        for (KafkaStream stream : streams) {
//            new Thread(new KafkaMessageProcessor(stream)).start();
        }

    }
    /**
     * 功能描述: 这个spout最终会运行在task中，这段代码最终会放在某个worker进程的某个executor线程内部的task中<br>
     * task会负责去无限循环调用此方法<br>
     * 只要的话，无限循环调用，可以不断发射最新的数据出去，形成一个数据流<br>
     *
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/9 23:37
     */
    public void nextTuple() {

    }
    /**
     * 功能描述: 定义一个你发射出去的每个tuple中的每个field的名称是什么<br>
     * 〈〉
     *
     * @param declarer
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/7/9 23:45
     */
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
