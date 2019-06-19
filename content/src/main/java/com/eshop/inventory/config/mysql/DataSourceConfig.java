package com.eshop.inventory.config.mysql;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Component
public class DataSourceConfig {


    /**
     * 配置数据源并指定前缀
     */
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource();
    }

    /**
     * 配置sqlSqssionFactory 用于mybatis的陪住
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //指定mybatis的xml扫描路径
//        mybatisSqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/*/*.xml"));
        return mybatisSqlSessionFactoryBean.getObject();
    }
    //定义 MybatisPlus 的全局策略配置
    @Bean
    public GlobalConfiguration globalConfiguration(){
        GlobalConfiguration c = new GlobalConfiguration();
        c.setDbColumnUnderline(true);
        c.setIdType(0);
        return c;
    }


    /**
     * 事务管理器
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
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
