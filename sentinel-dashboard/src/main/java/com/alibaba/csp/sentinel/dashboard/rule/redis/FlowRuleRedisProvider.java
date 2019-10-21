package com.alibaba.csp.sentinel.dashboard.rule.redis;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zeryts
 * @description: 限流存储操作取的redis类
 * -----------------------------------
 * @title: FlowRuleRedisProvider
 * @projectName amap
 * @date 2019/8/22 20:15
 */
@Component("flowRuleRedisProvider")
public class FlowRuleRedisProvider implements DynamicRuleProvider<List<FlowRuleEntity>> {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<FlowRuleEntity> getRules(String appName) throws Exception {
        Object rules = stringRedisTemplate.opsForValue().get(RuleConsts.RULE_FLOW + appName);
        if (rules == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseArray(rules.toString(),FlowRuleEntity.class);

    }
}
