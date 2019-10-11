package com.alibaba.csp.sentinel.dashboard.discovery;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * @author zeryts
 * @description: 存储在redis中的操作
 * -----------------------------------
 * @title: RedisMachineDiscovery
 * @projectName amap
 * @date 2019/9/9 16:46
 */
public class RedisMachineDiscovery implements  MachineDiscovery{
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public long addMachine(MachineInfo machineInfo) {
        AssertUtil.notNull(machineInfo, "machineInfo cannot be null");
        //获取到存储至redis的key
        String key = machineInfo.getApp() + RuleConsts.CLUSTER_MAP_DATA_ID ;
        String str = redisTemplate.opsForValue().get(key);
        //如果不存在
        if(str == null || str.trim().equals("") || str.equals("[]") ){
            AppInfo appInfo = new AppInfo(machineInfo.getApp(), machineInfo.getAppType());

        }
        //不存在则存储
        List<AppInfo> appInfos = JSON.parseArray(str, AppInfo.class);




        return 0;
    }

    @Override
    public boolean removeMachine(String app, String ip, int port) {
        return false;
    }

    @Override
    public List<String> getAppNames() {
        return null;
    }
    @Override
    public AppInfo getDetailApp(String app) {
        return null;
    }

    @Override
    public Set<AppInfo> getBriefApps() {
        return null;
    }

    @Override
    public void removeApp(String app) {

    }
}
