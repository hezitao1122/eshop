package com.alibaba.csp.sentinel.dashboard.rule.redis;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zeryts
 * @description: 系统参数操作的取值redis类
 * -----------------------------------
 * @title: SystemRuleRedisProvider
 * @projectName amap
 * @date 2019/8/23 15:50
 */
@Component(value = "systemRuleRedisProvider")
public class SystemRuleRedisProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public List<SystemRuleEntity> getRules(String appName) throws Exception {
        Object rules = stringRedisTemplate.opsForValue().get(RuleConsts.RULE_SYSTEM + appName);
        if (rules == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseArray(rules.toString(),SystemRuleEntity.class);
    }
}
