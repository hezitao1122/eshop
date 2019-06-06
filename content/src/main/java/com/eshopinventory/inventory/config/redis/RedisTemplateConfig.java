package com.eshopinventory.inventory.config.redis;//package com.eshopinventory.inventory.config.redis;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
///**
// * @description: 连接redis的工具类配置
// * @ClassName: RedisTemplateConfig
// * @author: hzt
// * @date: 2018-12-05 10:09
// */
//@Configuration
//public class RedisTemplateConfig {
//
//
//    @Bean
//    public RedisSerializer<Object> fastJson2JsonRedisSerializer() {
//        return new FastJson2JsonRedisSerializer<Object>(Object.class);
//    }
//
//
//
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisCF) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(redisCF);
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
//        redisTemplate.afterPropertiesSet();
//        return redisTemplate;
//    }
//}
