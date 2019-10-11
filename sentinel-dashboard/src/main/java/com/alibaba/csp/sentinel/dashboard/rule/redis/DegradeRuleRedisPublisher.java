package com.alibaba.csp.sentinel.dashboard.rule.redis;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.DegradeRuleEntity;
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
 * @description: 限流操作推送redis的类
 * -----------------------------------
 * @title: DegradeRuleRedisPublisher
 * @projectName amap
 * @date 2019/8/23 15:58
 */
@Component(value = "degradeRuleRedisPublisher")
public class DegradeRuleRedisPublisher implements DynamicRulePublisher<List<DegradeRuleEntity>> {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private InDBRuleConfigService service;
    @Override
    public void publish(String app, List<DegradeRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        String strs = JSON.toJSONString(rules);
        stringRedisTemplate.opsForValue().set(RuleConsts.RULE_DEGRADE + app, strs);
        //成功的话同步至数据库
        service.updateByAppAndType(app,RuleConsts.RULE_DEGRADE,strs);
        //成功的话再发送channel消息
        stringRedisTemplate.convertAndSend(RuleConsts.RULE_DEGRADE_CHANNEL + app , strs);
    }
}
