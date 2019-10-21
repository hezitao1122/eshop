package com.alibaba.csp.sentinel.dashboard.config;

import com.baomidou.mybatisplus.incrementer.OracleKeyGenerator;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zeryts
 * @description: MyBatisPlus的配置类
 * -----------------------------------
 * @title: BasicMyBatisPlusConfig
 * @projectName amap
 * @date 2019/8/13 15:45
 */
@Configuration
@MapperScan("com.alibaba.csp.sentinel.dashboard.repository.metric")
public class BasicMyBatisPlusConfig {
    /**
     * 分页插件
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public OracleKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }
}
