package com.eshop.inventory;

import com.eshop.inventory.config.kafka.InitKafkaListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @ClassName: ProductCacheApplication
 * @Title: ProductCacheApplication
 * @ProjectName: inventory
 * @Description: 用于缓存框架的启动类
 * @Author: hzt
 * @Date: Create in 2019/6/1317:11
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix //开启Hystrix支持
@EnableFeignClients //开启feign的注解
public class ProductCacheApplication {

    public static ApplicationContext app= null;

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
}
