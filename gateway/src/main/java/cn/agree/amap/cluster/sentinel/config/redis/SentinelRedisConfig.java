package cn.agree.amap.cluster.sentinel.config.redis;

import cn.agree.amap.cluster.sentinel.entity.ClusterGroupEntity;
import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.flow.rule.ClusterParamFlowRuleManager;
import com.alibaba.csp.sentinel.cluster.server.config.ClusterServerConfigManager;
import com.alibaba.csp.sentinel.cluster.server.config.ServerTransportConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.csp.sentinel.util.HostNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author zeryts
 * @description: 嵌入模式下的配置
 * -----------------------------------
 * @title: SentinelRedisConfig
 * @projectName amap
 * @date 2019/9/10 18:43
 */
@Slf4j
@Configuration
public class SentinelRedisConfig extends BaseSentinelRedisConfig
{

    /**
     * 嵌入模式（Embedded），即作为内置的 token server 与服务在同一进程中启动。
     * 在此模式下，集群中各个实例都是对等的，token server 和 client 可以随时进行转变，因此无需单独部署，
     * 灵活性比较好。但是隔离性不佳，需要限制 token server 的总 QPS，防止影响应用本身。
     * 嵌入模式适合某个应用集群内部的流控。
     *
     * --> Redis版本一定要支持Pub/Sub模式  像windows下的redis对Pub/Sub支持不友好，所以不能生效
     */
    @PostConstruct
    public void init(){
        //加载单机模式下所需的网关配置
        initConfig();
        log.info(">>>>>>>>>执行sentinel规则初始化");
        //TODO 这两个加载顺序不能错

        //加载server端配置
        loadServerConfig();

        //加载Client配置
        loadClientConfig();

    }

    /**
     * description: 加载server端的配置
     *
     * @Author: zeryts
     * @Date: 2019/9/2 15:56
     */
    public void loadServerConfig() {

        log.info(">>>>>>>>>指定Server的配置");
        // 集群通信时间配置 格式 -》[{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        ReadableDataSource<String, ServerTransportConfig> serverTransportDs = new RedisDataSource<>(config,
                AppNameUtil.getAppName() + RuleConsts.CLUSTER_MAP_DATA_ID,
                AppNameUtil.getAppName() +RuleConsts.GROUPID,
                source -> {
                    List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {
                    });
                    return Optional.ofNullable(groupList)
                            .flatMap(this::extractServerTransportConfig)
                            .orElse(null);
                });
        ClusterServerConfigManager.registerServerTransportProperty(serverTransportDs.getProperty());


//        log.info("ClusterGroupEntity -> [{}]",JSON.toJSONString(serverTransportDs.getProperty()));
        // Cluster map format:
        // 集群配置 格式 -》[{"clientSet":["112.12.88.66@8729","112.12.88.67@8727"],"ip":"112.12.88.68","machineId":"112.12.88.68@8728","port":11111}]
        // machineId: <ip@commandPort>, commandPort for port exposed to Sentinel dashboard (transport module)
        ReadableDataSource<String, Integer> clusterModeDs = new RedisDataSource<>(config,
                AppNameUtil.getAppName() + RuleConsts.CLUSTER_MAP_DATA_ID,
                AppNameUtil.getAppName() +RuleConsts.GROUPID,
                source -> {
                    List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {
                    });
                    return Optional.ofNullable(groupList)
                            .map(this::extractMode)
                            .orElse(ClusterStateManager.CLUSTER_NOT_STARTED);
                });
        ClusterStateManager.registerProperty(clusterModeDs.getProperty());
        log.info("Cluster map format -> [{}]", JSON.toJSONString(clusterModeDs.getProperty()));

        //集群限流
        // Register cluster flow rule property supplier which creates data source by namespace.
        // Flow rule dataId format: ${namespace}-flow-rules
        ClusterFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<FlowRule>> ds = new RedisDataSource<>(config,
                    RuleConsts.RULE_FLOW + namespace,
                    RuleConsts.RULE_FLOW_CHANNEL+namespace,
                    source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                    }));
            return ds.getProperty();
        });

        //集群热点规则
        // Register cluster parameter flow rule property supplier which creates data source by namespace.
        ClusterParamFlowRuleManager.setPropertySupplier(namespace -> {
            ReadableDataSource<String, List<ParamFlowRule>> ds = new RedisDataSource<>(config,
                    RuleConsts.PARAM_FLOW + namespace,
                    RuleConsts.PARAM_FLOW_POSTFIX_CHANNEL+namespace,
                    source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                    }));
            return ds.getProperty();
        });
    }

    /**
     * description: 加载client端的配置
     *
     * @Author: zeryts
     * @Date: 2019/9/2 15:57
     */
    public void loadClientConfig() {
        log.info(">>>>>>>>>指定为客户端集群的配置");
        //客户端通信配置（ClusterClientConfig），包括通信的超时时长等配置。
        // 我们可以通过 ClusterClientConfigManager 的 registerClientConfigProperty 方法注册动态配置源。
        ReadableDataSource<String, ClusterClientConfig> clientConfigDs =
                new RedisDataSource<>(config,
                        AppNameUtil.getAppName() + RuleConsts.CONFIG_ASSIGN_DATA_ID,
                        AppNameUtil.getAppName()+ RuleConsts.CONFIG_ASSIGN_DATA_ID_CHANNEL,
                        source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {
                        }));
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDs.getProperty());
        //客户端通信配置（ClusterClientConfig），包括通信的超时时长等配置。
        // 我们可以通过 ClusterClientConfigManager 的 registerClientConfigProperty 方法注册动态配置源。
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignDs = new RedisDataSource<>(config,
                AppNameUtil.getAppName() + RuleConsts.CLUSTER_MAP_DATA_ID,
                AppNameUtil.getAppName()+RuleConsts.GROUPID,
                source -> {
                    List<ClusterGroupEntity> groupList = JSON.parseObject(source, new TypeReference<List<ClusterGroupEntity>>() {
                    });
                    return Optional.ofNullable(groupList)
                            .flatMap(this::extractClientAssignment)
                            .orElse(null);
                });
        //客户端分配配置（ClusterClientAssignConfig），包括要连接的对端 token server 地址等相关信息。
        // 我们可以通过 ClusterClientConfigManager 的 registerServerAssignProperty 方法注册动态配置源。
        // 分配配置通常通过统一的分配表解析而来，
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignDs.getProperty());
        log.info("ClusterClientAssignConfig -> [{}]", JSON.toJSONString(clientAssignDs.getProperty()));
    }


    private Optional<ClusterClientAssignConfig> extractClientAssignment(List<ClusterGroupEntity> groupList) {
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return Optional.empty();
        }
        // Build client assign config from the client set of target server group.
        for (ClusterGroupEntity group : groupList) {
            if (group.getClientSet().contains(getCurrentMachineId())) {
                String ip = group.getIp();
                Integer port = group.getPort();
                return Optional.of(new ClusterClientAssignConfig(ip, port));
            }
        }
        return Optional.empty();
    }

    private boolean machineEqual(/*@Valid*/ ClusterGroupEntity group) {
        return getCurrentMachineId().equals(group.getMachineId());
    }

    private Optional<ServerTransportConfig> extractServerTransportConfig(List<ClusterGroupEntity> groupList) {
        return groupList.stream()
                .filter(this::machineEqual)
                .findAny()
                .map(e -> new ServerTransportConfig().setPort(e.getPort()).setIdleSeconds(600));
    }


    private int extractMode(List<ClusterGroupEntity> groupList) {
        // If any server group machineId matches current, then it's token server.
        if (groupList.stream().anyMatch(this::machineEqual)) {
            return ClusterStateManager.CLUSTER_SERVER;
        }
        // If current machine belongs to any of the token server group, then it's token client.
        // Otherwise it's unassigned, should be set to NOT_STARTED.
        boolean canBeClient = groupList.stream()
                .flatMap(e -> e.getClientSet().stream())
                .filter(Objects::nonNull)
                .anyMatch(e -> e.equals(getCurrentMachineId()));
        return canBeClient ? ClusterStateManager.CLUSTER_CLIENT : ClusterStateManager.CLUSTER_NOT_STARTED;
//        return ClusterStateManager.CLUSTER_CLIENT;
    }

    private String getCurrentMachineId() {
        // Note: this may not work well for container-based env.
//        return HostNameUtil.getIp() + SEPARATOR + TransportConfig.getRuntimePort();
        return HostNameUtil.getIp() + SEPARATOR + port;
    }

    private static final String SEPARATOR = "@";


}
