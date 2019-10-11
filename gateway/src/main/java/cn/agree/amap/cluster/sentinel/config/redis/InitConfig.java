package cn.agree.amap.cluster.sentinel.config.redis;

import cn.agree.amap.cluster.sentinel.config.redis.BaseSentinelRedisConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zeryts
 * @description: 初始化常量
 * -----------------------------------
 * @title: InitConfig
 * @projectName amap
 * @date 2019/9/12 14:12
 */
@Component
public class InitConfig {


    @Value("${spring.redis.host}")
    public void setRedisHost(String redisHost) {
        BaseSentinelRedisConfig.redisHost = redisHost;
    }
    @Value("${spring.redis.port}")
    public void setRedisPort(Integer redisPort) {
        BaseSentinelRedisConfig.redisPort = redisPort;
    }
    @Value("${spring.redis.password}")
    public void setPsd(String psd) {
        BaseSentinelRedisConfig.psd = psd;
    }
    @Value("${spring.cloud.sentinel.transport.port}")
    public void setPort(Integer port) {
//        System.out.println(port);
        BaseSentinelRedisConfig.port = port;
    }

}
