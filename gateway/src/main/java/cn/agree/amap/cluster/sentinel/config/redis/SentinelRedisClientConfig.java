package cn.agree.amap.cluster.sentinel.config.redis;

import cn.agree.amap.cluster.sentinel.config.redis.BaseSentinelRedisConfig;
import cn.agree.amap.cluster.sentinel.entity.ClusterGroupEntity;
import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.TokenServerDescriptor;
import com.alibaba.csp.sentinel.cluster.client.ClusterTokenClient;
import com.alibaba.csp.sentinel.cluster.client.DefaultClusterTokenClient;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientAssignConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfig;
import com.alibaba.csp.sentinel.cluster.client.config.ClusterClientConfigManager;
import com.alibaba.csp.sentinel.cluster.server.ClusterTokenServer;
import com.alibaba.csp.sentinel.cluster.server.SentinelDefaultTokenServer;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.redis.RedisDataSource;
import com.alibaba.csp.sentinel.datasource.redis.config.RedisConnectionConfig;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author zeryts
 * @description: 独立模式下客户端需要加载的配置
 * -----------------------------------
 * @title: SentinelRedisClientConfig
 * @projectName amap
 * @date 2019/9/10 18:45
 */
//@Configuration
@Slf4j
public class SentinelRedisClientConfig extends BaseSentinelRedisConfig {

    public void init() {
        initConfig();
        log.info(">>>>>>>>>执行sentinel规则初始化");
        // 指定当前身份为 Token Server
        ClusterStateManager.applyState(ClusterStateManager.CLUSTER_CLIENT);
        loadClientConfig();
        registerClusterClientProperty();

        // 启动
        try {
            // 创建一个 ClusterTokenServer 的实例，独立模式
            ClusterTokenClient clusterTokenClient =  new DefaultClusterTokenClient();
            System.out.println(clusterTokenClient.currentServer().getHost());
            System.out.println(clusterTokenClient.currentServer().getPort());
            clusterTokenClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadServerConfig() {

    }
    @Override
    public void loadClientConfig(){
        ClusterClientAssignConfig assignConfig = new ClusterClientAssignConfig();
        assignConfig.setServerHost("192.168.194.116");
        assignConfig.setServerPort(11111);
        ClusterClientConfigManager.applyNewAssignConfig(assignConfig);

        ClusterClientConfig clientConfig = new ClusterClientConfig();
        clientConfig.setRequestTimeout(200);
        ClusterClientConfigManager.applyNewConfig(clientConfig);
    }

    /**
     * 为ClusterClientConfig注册一个SentinelProperty
     * 这样的话可以动态的更改这些配置
     */
    private void registerClusterClientProperty() {
        String clientConfigDataId = "cluster-client-config";
        // 初始化一个配置ClusterClientConfig的 Nacos 数据源
        ReadableDataSource<String, ClusterClientConfig> clientConfigDS = new RedisDataSource<>(config,
                AppNameUtil.getAppName()+ RuleConsts.CONFIG_DATA_ID,
                RuleConsts.CONFIG_DATA_ID_CHANNEL,
                source -> JSON.parseObject(source, new TypeReference<ClusterClientConfig>() {}));
        ClusterClientConfigManager.registerClientConfigProperty(clientConfigDS.getProperty());

        String clientAssignConfigDataId = "cluster-client-assign-config";
        // 初始化一个配置ClusterClientAssignConfig的 Nacos 数据源
        ReadableDataSource<String, ClusterClientAssignConfig> clientAssignConfigDS = new RedisDataSource<>(config,
                AppNameUtil.getAppName()+RuleConsts.CONFIG_ASSIGN_DATA_ID,
                RuleConsts.CONFIG_ASSIGN_DATA_ID_CHANNEL,
                source -> JSON.parseObject(source, new TypeReference<ClusterClientAssignConfig>() {}));
        ClusterClientConfigManager.registerServerAssignProperty(clientAssignConfigDS.getProperty());

    }
}
