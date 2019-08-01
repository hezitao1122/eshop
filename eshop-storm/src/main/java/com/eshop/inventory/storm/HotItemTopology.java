package com.eshop.inventory.storm;

import com.eshop.inventory.storm.config.KafkaConsumerConfig;

/**
 * @author zeryts
 * @description: 商品详情热数据统计的拓扑
 * ```````````````````````````
 * @title: HotItemTopology
 * @projectName inventory
 * @date 2019/8/1 22:56
 */
public class HotItemTopology {
    public static void main(String[] args) {
        KafkaConsumerConfig.init();
    }
}
