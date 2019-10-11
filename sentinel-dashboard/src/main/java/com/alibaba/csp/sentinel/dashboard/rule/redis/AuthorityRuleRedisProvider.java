package com.alibaba.csp.sentinel.dashboard.rule.redis;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.InDBRuleConfigRepository;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.fastjson.JSONObject;
import com.eshop.inventory.common.enums.RuleConsts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zeryts
 * @description: 授权规则取值redis类
 * -----------------------------------
 * @title: SystemRuleRedisProvider
 * @projectName amap
 * @date 2019/8/23 15:50
 */
@Component(value = "authorityRuleRedisProvider")
public class AuthorityRuleRedisProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        Object rules = stringRedisTemplate.opsForValue().get(RuleConsts.AUTH_SYSTEM + appName);
        if (rules == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseArray(rules.toString(),AuthorityRuleEntity.class);
    }
}
