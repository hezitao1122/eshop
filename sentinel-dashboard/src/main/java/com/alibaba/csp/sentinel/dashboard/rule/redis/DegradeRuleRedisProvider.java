package com.alibaba.csp.sentinel.dashboard.rule.redis;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.InDBRuleConfigRepository;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zeryts
 * @description: 限流规则操作的取得redis的类
 * -----------------------------------
 * @title: DegradeRuleRedisProvider
 * @projectName amap
 * @date 2019/8/23 15:55
 */
@Component(value = "degradeRuleRedisProvider")
public class DegradeRuleRedisProvider implements DynamicRuleProvider<List<DegradeRuleEntity>> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public List<DegradeRuleEntity> getRules(String appName) throws Exception {
        Object rules = stringRedisTemplate.opsForValue().get(RuleConsts.RULE_DEGRADE + appName);
        if (rules == null) {
            return new ArrayList<>();
        }
        return JSONObject.parseArray(rules.toString(),DegradeRuleEntity.class);

    }
}
