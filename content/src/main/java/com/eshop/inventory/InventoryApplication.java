package com.eshop.inventory;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Properties;

@SpringBootApplication
@MapperScan(basePackages = "com.eshop.inventory.manage.*.mapper")
public class InventoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }


    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper(){
        System.out.println("开始配置数据分页插件");
        PageHelper pageHelper = new PageHelper();
        // properties暂时放到这里，后期放到配置文件
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum","true");
        properties.setProperty("rowBoundsWithCount","true");
        properties.setProperty("reasonable","true");
        //配置mysql数据库的方言
        properties.setProperty("dialect","mysql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }


}
