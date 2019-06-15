package com.eshop.inventory.config.kafka;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author zeryts
 * @description: kafka用的系统初始化的监听器
 * ```````````````````````````
 * @title: InitKafkaListener
 * @projectName inventory
 * @date 2019/6/15 15:21
 */
public class InitKafkaListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /**
         * 创建一个kafka的消费者，消费的topic为 cache-message
         */
        new Thread(new KafkaConsumer("cache-message")).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
