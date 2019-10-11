package com.alibaba.csp.sentinel.dashboard.rule.redis;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zeryts
 * @description: 参数规则取值redis类
 * -----------------------------------
 * @title: ParamFlowRuleRedisProvider
 * @projectName amap
 * @date 2019/8/23 15:50
 */
@Component(value = "paramFlowRuleRedisProvider")
public class ParamFlowRuleRedisProvider implements DynamicRuleProvider<List<ParamFlowRuleEntity>> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public List<ParamFlowRuleEntity> getRules(String appName) throws Exception {
        Object rules = stringRedisTemplate.opsForValue().get(RuleConsts.PARAM_FLOW + appName);
        if (rules == null) {
            return new ArrayList<>();
        }
        //,ParamFlowRuleEntity.class
        return JSONObject.parseArray(rules.toString(), ParamFlowRuleEntity.class);
    }
}
