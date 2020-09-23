package com.eshop.inventory.config.thread;//package com.eshop.inventory.config.thread;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.concurrent.Executor;
//
///**
// * @Description:
// * @Author: ybc
// * @Date: 2018/6/2116:17
// * @Version: 1.0
// */
//@Configuration
//@EnableAsync
//public class AsynConfig {
//
//    /*
//   此处成员变量应该使用@Value从配置中读取
//    */
//    @Value("#{thread.corePoolSize}")
//    private String corePoolSize;
//    @Value("#{thread.maxPoolSize}")
//    private String maxPoolSize ;
//    @Value("#{thread.queueCapacity}")
//    private String queueCapacity ;
//
//    @Bean
//    public Executor taskExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(Integer.valueOf(corePoolSize));
//        executor.setMaxPoolSize(Integer.valueOf(maxPoolSize));
//        executor.setQueueCapacity(Integer.valueOf(queueCapacity));
//        executor.initialize();
//        return executor;
//    }
//}
