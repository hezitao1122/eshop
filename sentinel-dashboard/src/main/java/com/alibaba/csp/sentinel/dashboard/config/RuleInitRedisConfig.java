package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.RuleConfiEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.InDBRuleConfigService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author zeryts
 * @description: 将存储至数据库的Rule同步至redis的类
 * -----------------------------------
 * @title: RuleInitRedisConfig
 * @projectName amap
 * @date 2019/9/3 15:28
 */
@Configuration
public class RuleInitRedisConfig {

    @Autowired
    private InDBRuleConfigService service;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /** description: 项目启动的时候将数据库的Rule信息同步至redis 中
     * @Author: zeryts
     * @Date: 2019/9/3 15:39
     */
    @PostConstruct
    public void initRule(){
        List<RuleConfiEntity> entities = service.findAll();
        for (RuleConfiEntity entity:entities) {
            String key = entity.getType()+entity.getAppName();
            String content = entity.getContent();
            Object map = JSON.parse(content);
            redisTemplate.opsForValue().set(key,JSON.toJSONString(map));
        }
    }
}
