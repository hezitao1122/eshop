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
    String RULE_FLOW = "sentinel_rule_flow_";
    String RULE_FLOW_CHANNEL = "sentinel_rule_flow_channel_";

    //限流规则key前缀
    String RULE_DEGRADE = "sentinel_rule_degrade_";
    String RULE_DEGRADE_CHANNEL = "sentinel_rule_degrade_channel_";

    //系统规则key前缀
    String RULE_SYSTEM = "sentinel_rule_system_";
    String RULE_SYSTEM_CHANNEL = "sentinel_rule_system_channel_";

    //授权规则key前缀
    String AUTH_SYSTEM = "sentinel_rule_auth_";
    String AUTH_SYSTEM_CHANNEL = "sentinel_rule_auth_channel_";

    //热点规则key前缀
    String PARAM_FLOW = "sentinel_rule_param_";
    String PARAM_FLOW_CHANNEL = "sentinel_rule_param_channel_";

    //cluster的配置
    String GROUPID = "SENTINEL_GROUP";
    String NAMESPACE_SET = "cluster-server-namespace-set";
    String TRANSPORT_CONFIG = "cluster-server-transport-config";
    String CONFIG_DATA_ID = "-cluster-client-config";
    String CONFIG_DATA_ID_CHANNEL = "-cluster-client-config-channel";
    String CONFIG_ASSIGN_DATA_ID = "-cluster-client-config-assign";
    String CONFIG_ASSIGN_DATA_ID_CHANNEL = "-cluster-client-config-assign-channel";
    String CLUSTER_MAP_DATA_ID = "-cluster-map";
    String FLOW_POSTFIX = "-flow-rules";
    String FLOW_POSTFIX_CHANNEL = "-flow-rules-chennel";
    String PARAM_FLOW_POSTFIX = "-param-rules";
    String PARAM_FLOW_POSTFIX_CHANNEL = "-param-rules-channel";


}
