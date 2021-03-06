package com.eshop.inventory.storm.processor;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author zeryts
 * @description: 接受kafka消息的消费者类
 * ```````````````````````````
 * @title: KafkaMessageProcessor
 * @projectName inventory
 * @date 2019/8/1 23:56
 */
public class KafkaMessageProcessor implements Runnable {

    private KafkaStream kafkaStream;

    private ArrayBlockingQueue<String> queue;

    public KafkaMessageProcessor(KafkaStream kafkaStream, ArrayBlockingQueue<String> queue) {
        this.kafkaStream = kafkaStream;
        this.queue = queue;
    }


    public void run() {
        //拿到消息的迭代器，迭代器就可以拿到一条条的message
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            ////此处代表拿到具体的message消息
            String message = new String(it.next().message());

            //put进Spout的Queue中
            try {
                queue.put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //转化为对象
//            KafkaBaseVo kafkaBaseVo = JSON.parseObject(message, KafkaBaseVo.class);
            /**
             * 主动监听：
             * 监听kafka队列，获取到一个商品变更的消息之后，去哪个源服务中调用接口拉取数据，更新到ehcache和redis中
             * 先获取分布式锁，然后才能更新redis，同时需要比较redis的版本
             */


        }
    }
}
