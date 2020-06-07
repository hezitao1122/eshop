package com.eshop.inventory;

import com.eshop.inventory.config.kafka.InitKafkaListener;
import com.eshop.inventory.hystrixy.filter.HystrixRequestContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @ClassName: ProductCacheApplication
 * @Title: ProductCacheApplication
 * @ProjectName: inventory
 * @Description: 用于缓存框架的启动类
 * @Author: zeryts
 * @Date: Create in 2019/6/1317:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix //开启Hystrix支持
@EnableFeignClients //开启feign的注解
@EnableRetry //开启重试机制

public class ProductCacheApplication {

    public static ApplicationContext app = null;

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ProductCacheApplication.class, args);
        app = run;
    }


    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean =
                new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new InitKafkaListener());
        return servletListenerRegistrationBean;
    }

    /**
     * 功能描述: 注册一个Filter,用于Hystrix的上下文操作<br>
     * 〈〉
     *
     * @return: org.springframework.boot.web.servlet.FilterRegistrationBean<com.eshop.inventory.hystrixy.filter.HystrixRequestContextFilter>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2020/5/16 11:44
     */
    @Bean
    public FilterRegistrationBean<HystrixRequestContextFilter> hystrixRegisterBean() {
        FilterRegistrationBean<HystrixRequestContextFilter> registration = new FilterRegistrationBean<>(new HystrixRequestContextFilter());
        /**
         * 代表需要缓存的路径
         */
        registration.addUrlPatterns("/*");
        return registration;
    }

}
