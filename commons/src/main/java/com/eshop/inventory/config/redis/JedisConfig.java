package com.eshop.inventory.config.redis;//package com.eshop.inventory.config.redis;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Configuration
//@Component
//public class JedisConfig {
//
//    /**
//     * 配置redis-cluster集群
//     * @return
//     */
//    @Bean
//    public JedisCluster JedisClusterFactory() {
//        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
//        jedisClusterNodes.add(new HostAndPort("192.168.31.224", 7005));
//        jedisClusterNodes.add(new HostAndPort("192.168.31.226", 7002));
//        jedisClusterNodes.add(new HostAndPort("192.168.31.225", 7004));
//        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
//        return jedisCluster;
//    }
//
//}
