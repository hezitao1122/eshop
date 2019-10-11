package cn.agree.amap.cluster.sentinel.config.redis;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.eshop.inventory.config.sentinel.IBaseSentinelConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author zeryts
 * @description: Redis配置类的父类
 * -----------------------------------
 * @title: BaseSentinelRedisConfig
 * @projectName amap
 * @date 2019/9/10 18:48
 */
@Slf4j
@Configuration
public abstract class BaseSentinelRedisConfig implements IBaseSentinelConfig {


    public static String redisHost;

    public static Integer redisPort;

    public static String psd;
    public static Integer port;

    protected RedisConnectionConfig config;


    public void initConfig(){
        config = RedisConnectionConfig.builder().withHost(redisHost).withPort(redisPort).withPassword(psd).withDatabase(6).build();
        standOnlyConfig();
    }

    /** description: 网关单机模式下的配置
     * @Author: zeryts
     * @Date: 2019/8/29 15:49
     */
    public void standOnlyConfig(){
        log.info(">>>>>>>>>指定Rule的配置");
        Converter<String, List<FlowRule>> parser = source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        });
        //流控规则改为集群模式
        ReadableDataSource<String, List<FlowRule>> redisDataSource = new RedisDataSource<>(config, RuleConsts.RULE_FLOW + SentinelConfig.getAppName(), RuleConsts.RULE_FLOW_CHANNEL + SentinelConfig.getAppName(), parser);
        FlowRuleManager.register2Property(redisDataSource.getProperty());

        log.info(">>>>>>>>>指定ParamRule的配置");
        Converter<String, List<ParamFlowRule>> parser4 = source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
        });
        ReadableDataSource<String, List<ParamFlowRule>> redisDataSource4 = new RedisDataSource<>(config, RuleConsts.PARAM_FLOW + SentinelConfig.getAppName(), RuleConsts.PARAM_FLOW_CHANNEL + SentinelConfig.getAppName(), parser4);
        ParamFlowRuleManager.register2Property(redisDataSource4.getProperty());

        log.info(">>>>>>>>>指定DegradeRule的配置");
        Converter<String, List<DegradeRule>> parser1 = source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
        });
        ReadableDataSource<String, List<DegradeRule>> redisDataSource1 = new RedisDataSource<>(config, RuleConsts.RULE_DEGRADE + SentinelConfig.getAppName(), RuleConsts.RULE_DEGRADE_CHANNEL + SentinelConfig.getAppName(), parser1);
        DegradeRuleManager.register2Property(redisDataSource1.getProperty());
        log.info(">>>>>>>>>指定SystemRule的配置");
        Converter<String, List<SystemRule>> parser2 = source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
        });
        ReadableDataSource<String, List<SystemRule>> redisDataSource2 = new RedisDataSource<>(config, RuleConsts.RULE_SYSTEM + SentinelConfig.getAppName(), RuleConsts.RULE_SYSTEM_CHANNEL + SentinelConfig.getAppName(), parser2);
        SystemRuleManager.register2Property(redisDataSource2.getProperty());
        log.info(">>>>>>>>>指定AuthorityRule的配置");
        Converter<String, List<AuthorityRule>> parser3 = source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
        });
        ReadableDataSource<String, List<AuthorityRule>> redisDataSource3 = new RedisDataSource<>(config, RuleConsts.AUTH_SYSTEM + SentinelConfig.getAppName(), RuleConsts.AUTH_SYSTEM_CHANNEL + SentinelConfig.getAppName(), parser3);
        AuthorityRuleManager.register2Property(redisDataSource3.getProperty());

    }

}
