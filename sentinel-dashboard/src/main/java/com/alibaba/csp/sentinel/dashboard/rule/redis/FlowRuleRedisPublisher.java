package com.alibaba.csp.sentinel.dashboard.rule.redis;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.repository.metric.InDBRuleConfigRepository;
import com.alibaba.csp.sentinel.dashboard.repository.metric.InDBRuleConfigService;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zeryts
 * @description: 限流存储操作存储的redis类
 * -----------------------------------
 * @title: FlowRuleRedisPublisher
 * @projectName amap
 * @date 2019/8/22 20:24
 */
@Component("flowRuleRedisPublisher")
public class FlowRuleRedisPublisher implements DynamicRulePublisher<List<FlowRuleEntity>> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private InDBRuleConfigService service;
    @Override
    public void publish(String app, List<FlowRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        String strs = JSON.toJSONString(rules);
        stringRedisTemplate.opsForValue().set(RuleConsts.RULE_FLOW + app, strs);
        //成功的话同步至数据库
        service.updateByAppAndType(app,RuleConsts.RULE_FLOW,strs);
        //成功的话再发送channel消息
        stringRedisTemplate.convertAndSend(RuleConsts.RULE_FLOW_CHANNEL + app , strs);
    }
}
