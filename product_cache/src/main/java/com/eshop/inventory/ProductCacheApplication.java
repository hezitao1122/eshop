package com.eshop.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
public class ProductCacheApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductCacheApplication.class, args);
    }
}
