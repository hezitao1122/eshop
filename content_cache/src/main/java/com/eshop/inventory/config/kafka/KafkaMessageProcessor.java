package com.eshop.inventory.config.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

/**
 * @author zeryts
 * @description: Kafka的消息处理线程
 * ```````````````````````````
 * @title: KafkaMessageProcessor
 * @projectName inventory
 * @date 2019/6/15 15:35
 */
public class KafkaMessageProcessor implements Runnable{

    private KafkaStream kafkaStream;

    public KafkaMessageProcessor(KafkaStream kafkaStream) {
        this.kafkaStream = kafkaStream;
    }
    /**
     * 2、编写业务逻辑
     *
     * （1）两种服务会发送来数据变更消息：商品信息服务，商品店铺信息服务，每个消息都包含服务名以及商品id
     *
     * （2）接收到消息之后，根据商品id到对应的服务拉取数据，这一步，我们采取简化的模拟方式，就是在代码里面写死，会获取到什么数据，不去实际再写其他的服务去调用了
     *
     * （3）商品信息：id，名称，价格，图片列表，商品规格，售后信息，颜色，尺寸
     *
     * （4）商品店铺信息：其他维度，用这个维度模拟出来缓存数据维度化拆分，id，店铺名称，店铺等级，店铺好评率
     *
     * （5）分别拉取到了数据之后，将数据组织成json串，然后分别存储到ehcache中，和redis缓存中
     */
    @Override
    public void run() {
        //拿到消息的迭代器，迭代器就可以拿到一条条的message
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            ////此处代表拿到具体的message消息
            String message = new String(it.next().message());
            //转化为对象
            JSONObject messageObj = JSON.parseObject(message);


        }
    }
}
