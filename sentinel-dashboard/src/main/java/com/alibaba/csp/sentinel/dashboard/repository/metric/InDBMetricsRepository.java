package com.alibaba.csp.sentinel.dashboard.repository.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author zeryts
 * @description: 存储至数据库中的监控数据存储
 * -----------------------------------
 * @title: InDBMetricsRepository
 * @projectName amap
 * @date 2019/8/12 17:37
 */
@Component(value = "inDBMetricsRepository")
public class InDBMetricsRepository implements MetricsRepository<MetricEntity>{

    @Autowired
    private MetricsMapper repository;


    /** description:  存储单个监控数据
     * @param entity
     * @Author: zeryts
     * @Date: 2019/8/12 17:44
     */
    @Override
    public void save(MetricEntity entity) {
        if (entity == null || StringUtil.isBlank(entity.getApp())) {
            return;
        }
        repository.insert(entity);
    }

    /** description: 存储所有监控数据
     * @param entity
     * @Author: zeryts
     * @Date: 2019/8/12 17:44
     */
    @Override
    public void saveAll(Iterable<MetricEntity> entity) {
        if (entity == null) {
            return;
        }
        entity.forEach(e->repository.insert(e));
    }
    /** description: 查询某段时间内的某个应用的某个资源的监控数据
     * @param app appId
     * @param resource 资源id
     * @param startTime 查询开始时间
     * @param endTime 查询结束时间
     * @return: java.util.List<com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity>
     * @Author: zeryts
     * @Date: 2019/8/12 17:46
     */
    @Override
    public List<MetricEntity> queryByAppAndResourceBetween(String app, String resource, long startTime, long endTime) {
        List<MetricEntity> results = new ArrayList<>();
        if (StringUtil.isBlank(app)) {
            return results;
        }
        EntityWrapper<MetricEntity> wrapper = new EntityWrapper<>();
        //根据appid和resource和startTime和entTime联合查询
        wrapper.eq("APP",app);
        wrapper.eq("RESOURCE_ID",resource);
//        wrapper.where("TIME_STAMP >={0}",new Timestamp(startTime)).and("TIME_STAMP<={0}",new Timestamp(endTime));
//        wrapper.between("TIME_STAMP",new Timestamp(startTime),new Timestamp(endTime));
        wrapper.ge("TIME_STAMP",new Timestamp(startTime)).le("TIME_STAMP",new Timestamp(endTime));
        results = repository.selectList(wrapper);
        return results;
    }
    /** description: 查询某个应用下的所有资源
     * @param app
     * @return: java.util.List<java.lang.String>
     * @Author: zeryts
     * @Date: 2019/8/12 17:47
     */
    @Override
    public List<String> listResourcesOfApp(String app) {
        List<String> results = new ArrayList<>();
        if (StringUtil.isBlank(app)) {
            return results;
        }
        Map<String, MetricEntity> resourceCount = new ConcurrentHashMap<>(32);

        EntityWrapper<MetricEntity> wrapper = new EntityWrapper<>();
        //根据appid和resource和startTime和entTime联合查询
        wrapper.eq("APP",app);

        List<MetricEntity> lis = repository.selectList(wrapper);

        //获取该应用下的所有资源
        for (MetricEntity entity:lis) {
            //如果这个资源存在
            if (resourceCount.containsKey(entity.getResource())) {
                MetricEntity oldEntity = resourceCount.get(entity.getResource());
                oldEntity.addPassQps(entity.getPassQps());
                oldEntity.addRtAndSuccessQps(entity.getRt(), entity.getSuccessQps());
                oldEntity.addBlockQps(entity.getBlockQps());
                oldEntity.addExceptionQps(entity.getExceptionQps());
                oldEntity.addCount(1);
            } else {
                //如果这个资源部存在
                resourceCount.put(entity.getResource(), MetricEntity.copyOf(entity));
            }
        }
        return resourceCount.entrySet()
                .stream()
                .sorted((o1, o2) -> {
                    MetricEntity e1 = o1.getValue();
                    MetricEntity e2 = o2.getValue();
                    int t = e2.getBlockQps().compareTo(e1.getBlockQps());
                    if (t != 0) {
                        return t;
                    }
                    return e2.getPassQps().compareTo(e1.getPassQps());
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
