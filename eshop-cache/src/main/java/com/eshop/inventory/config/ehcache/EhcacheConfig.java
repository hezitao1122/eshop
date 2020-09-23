package com.eshop.inventory.config.ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * @author zeryts
 * @description: ehcache的配置类
 * ```````````````````````````
 * @title: EhcacheConfig
 * @projectName inventory
 * @date 2019/6/12 21:35
 */
@Configuration
@EnableCaching //启用ehcache的注解
public class EhcacheConfig {
    /**
     * 功能描述: 初始化ehcache缓存的工厂类<br>
     *
     * @return: org.springframework.cache.ehcache.EhCacheManagerFactoryBean
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/12 22:02
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
        factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        factoryBean.setShared(true);
        return factoryBean;
    }

    /**
     * 功能描述: 初始化<br>
     * 〈〉
     *
     * @param factoryBean 工厂管理类
     * @return: org.springframework.cache.ehcache.EhCacheCacheManager
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/12 22:07
     */
    @Autowired
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean factoryBean) {
        return new EhCacheCacheManager(factoryBean.getObject());
    }


}
