package com.eshop.inventory.common.enums;

/**
 * @author zeryts
 * @description: 存储常量的类
 * -----------------------------------
 * @title: RuleConsts
 * @projectName amap
 * @date 2019/8/22 20:19
 */
public interface RuleConsts {

    //流控规则key前缀
    public final String RULE_FLOW = "sentinel_rule_flow_";
    public final String RULE_FLOW_CHANNEL = "sentinel_rule_flow_channel_";

    //限流规则key前缀
    public final String RULE_DEGRADE = "sentinel_rule_degrade_";
    public final String RULE_DEGRADE_CHANNEL = "sentinel_rule_degrade_channel_";

    //系统规则key前缀
    public final String RULE_SYSTEM = "sentinel_rule_system_";
    public final String RULE_SYSTEM_CHANNEL = "sentinel_rule_system_channel_";

    //授权规则key前缀
    public final String AUTH_SYSTEM = "sentinel_rule_auth_";
    public final String AUTH_SYSTEM_CHANNEL = "sentinel_rule_auth_channel_";

    //热点规则key前缀
    public final String PARAM_FLOW = "sentinel_rule_param_";
    public final String PARAM_FLOW_CHANNEL = "sentinel_rule_param_channel_";

    //cluster的配置
    public final String GROUPID = "SENTINEL_GROUP";
    public final String NAMESPACE_SET = "cluster-server-namespace-set";
    public final String TRANSPORT_CONFIG = "cluster-server-transport-config";
    public final String CONFIG_DATA_ID =  "-cluster-client-config";
    public final String CONFIG_DATA_ID_CHANNEL =  "-cluster-client-config-channel";
    public final String CONFIG_ASSIGN_DATA_ID =  "-cluster-client-config-assign";
    public final String CONFIG_ASSIGN_DATA_ID_CHANNEL =  "-cluster-client-config-assign-channel";
    public final String CLUSTER_MAP_DATA_ID = "-cluster-map";
    String FLOW_POSTFIX = "-flow-rules";
    String FLOW_POSTFIX_CHANNEL = "-flow-rules-chennel";
    String PARAM_FLOW_POSTFIX = "-param-rules";
    String PARAM_FLOW_POSTFIX_CHANNEL = "-param-rules-channel";


}
