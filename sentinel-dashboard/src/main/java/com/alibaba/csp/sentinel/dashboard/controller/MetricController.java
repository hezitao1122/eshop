/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.ResourceEntity;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.repository.metric.MetricsMapper;
import com.alibaba.csp.sentinel.dashboard.repository.metric.MetricsRepository;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.csp.sentinel.util.StringUtil;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.alibaba.csp.sentinel.dashboard.domain.vo.MetricVo;

import javax.annotation.Resource;

/**
 * @author leyou
 */
@Controller
@RequestMapping(value = "/metric", produces = MediaType.APPLICATION_JSON_VALUE)
public class MetricController {

    private static Logger logger = LoggerFactory.getLogger(MetricController.class);

    private static final long maxQueryIntervalMs = 1000 * 60 * 300;

    @Resource
    @Qualifier(value = "inDBMetricsRepository")
    private MetricsRepository<MetricEntity> metricStore;
    @Autowired
    private MetricsMapper repository;

    @Value("${sentinel.dashboard.data.timeOut}")
    public String timeOut;

    @ResponseBody
    @PostMapping("/queryTopResourceMetric.json")
    public Result<?> queryTopResourceMetric(@RequestBody ResourceEntity entity) {
        String app = entity.getApp();
        Integer pageIndex = entity.getPageIndex();
        Integer pageSize = entity.getPageSize();
        Boolean desc = entity.getDesc();
        Long startTime = entity.getStartTime();
        Long endTime = entity.getEndTime();
        String searchKey = entity.getSearchKey();
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        if (pageIndex == null || pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize == null) {
            pageSize = 6;
        }
        if (pageSize >= 20) {
            pageSize = 20;
        }
        if (desc == null) {
            desc = true;
        }
        if (endTime == null) {
            endTime = System.currentTimeMillis();
        }
        if (startTime == null) {
            startTime = endTime - 1000 * 60 * Integer.valueOf(timeOut);
        }
        if (endTime - startTime > maxQueryIntervalMs) {
            return Result.ofFail(-1, "time intervalMs is too big, must <= 1h");
        }
//        List<String> resources = metricStore.listResourcesOfApp(app);
        List<String> resources = repository.queryResourcesByAppOrderByPassQps(app);
        logger.debug("queryTopResourceMetric(), resources.size()={}", resources.size());

        if (resources == null || resources.isEmpty()) {
            return Result.ofSuccess(null);
        }
        if (!desc) {
            Collections.reverse(resources);
        }
        if (StringUtil.isNotEmpty(searchKey)) {
            List<String> searched = new ArrayList<>();
            for (String resource : resources) {
                if (resource.contains(searchKey)) {
                    searched.add(resource);
                }
            }
            resources = searched;
        }
        int totalPage = (resources.size() + pageSize - 1) / pageSize;
        List<String> topResource = new ArrayList<>();
        if (pageIndex <= totalPage) {
            topResource = resources.subList((pageIndex - 1) * pageSize,
                    Math.min(pageIndex * pageSize, resources.size()));
        }
        final Map<String, Iterable<MetricVo>> map = new ConcurrentHashMap<>();
        logger.debug("topResource={}", topResource);
        long time = System.currentTimeMillis();
        for (final String resource : topResource) {
            List<MetricEntity> entities = metricStore.queryByAppAndResourceBetween(
                    app, resource, startTime, endTime);
            logger.debug("resource={}, entities.size()={}", resource, entities == null ? "null" : entities.size());
            List<MetricVo> vos = MetricVo.fromMetricEntities(entities, resource);
            Iterable<MetricVo> vosSorted = sortMetricVoAndDistinct(vos);
            map.put(resource, vosSorted);
        }
        logger.debug("queryTopResourceMetric() total query time={} ms", System.currentTimeMillis() - time);
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("totalCount", resources.size());
        resultMap.put("totalPage", totalPage);
        resultMap.put("pageIndex", pageIndex);
        resultMap.put("pageSize", pageSize);

        Map<String, Iterable<MetricVo>> map2 = new LinkedHashMap<>();
        // order matters.
        for (String identity : topResource) {
            map2.put(identity, map.get(identity));
        }
        resultMap.put("metric", map2);
        resultMap.put("resources", topResource);
        return Result.ofSuccess(resultMap);
    }

    @ResponseBody
    @RequestMapping("/queryByAppAndResource.json")
    public Result<?> queryByAppAndResource(@RequestBody ResourceEntity entity) {
        String app = entity.getApp();
        String identity = entity.getResource();
        Long startTime = entity.getStartTime();
        Long endTime = entity.getEndTime();
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        if (StringUtil.isEmpty(identity)) {
            return Result.ofFail(-1, "identity can't be null or empty");
        }
        if (endTime == null) {
            endTime = System.currentTimeMillis();
        }
        if (startTime == null) {
            startTime = endTime - 1000 * 60;
        }
        if (endTime - startTime > maxQueryIntervalMs) {
            return Result.ofFail(-1, "time intervalMs is too big, must <= 1h");
        }
        List<MetricEntity> entities = metricStore.queryByAppAndResourceBetween(
                app, identity, startTime, endTime);
        List<MetricVo> vos = MetricVo.fromMetricEntities(entities, identity);
        return Result.ofSuccess(sortMetricVoAndDistinct(vos));
    }

    @ResponseBody
    @RequestMapping("/queryApps.json")
    public Result<?> queryApps() {
        //查找有数据的app
        List<String> list = repository.selectAllApp();
        return Result.ofSuccess(list);
    }

    @ResponseBody
    @RequestMapping("/queryResourcesByApp.json")
    public Result<?> queryResourcesByApp(@RequestBody ResourceEntity entity) {
        if (entity == null || StringUtil.isEmpty(entity.getApp())) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        //查找有数据的app
        List<String> list = repository.queryResourcesByAppOrderByPassQps(entity.getApp());
        return Result.ofSuccess(list);
    }


    private Iterable<MetricVo> sortMetricVoAndDistinct(List<MetricVo> vos) {
        if (vos == null) {
            return null;
        }
        Map<Long, MetricVo> map = new TreeMap<>();
        for (MetricVo vo : vos) {
            MetricVo oldVo = map.get(vo.getTimestamp());
            if (oldVo == null || vo.getGmtCreate() > oldVo.getGmtCreate()) {
                map.put(vo.getTimestamp(), vo);
            }
        }
        return map.values();
    }

    @ResponseBody
    @RequestMapping("/queryTopResourceMetricByAppAndResource.json")
    public Result<?> queryTopResourceMetricByAppAndResource(@RequestBody ResourceEntity entity) {
        String app = entity.getApp();
        String resource = entity.getResource();
        Integer pageIndex = entity.getPageIndex();
        Integer pageSize = entity.getPageSize();
        Long startTime = entity.getStartTime();
        Long endTime = entity.getEndTime();
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app can't be null or empty");
        }
        if (pageIndex == null || pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize == null) {
            pageSize = 6;
        }
        if (pageSize >= 20) {
            pageSize = 20;
        }
        if (endTime == null) {
            endTime = System.currentTimeMillis();
        }
        if (startTime == null) {
            startTime = endTime - 1000 * 60 * Integer.valueOf(timeOut);
        }
        if (endTime - startTime > maxQueryIntervalMs) {
            return Result.ofFail(-1, "time intervalMs is too big, must <= 1h");
        }
        EntityWrapper<MetricEntity> wrapper = new EntityWrapper<>();
        wrapper.where("APP={0}", app);
        wrapper.where("RESOURCE_ID={0}", resource);
        wrapper.ge("TIME_STAMP", new Timestamp(startTime)).le("TIME_STAMP", new Timestamp(endTime));
        //查询总条数
        Integer totalCount = repository.selectCount(wrapper);

        //查询这段时间的总数据
        List<MetricEntity> entities = metricStore.queryByAppAndResourceBetween(
                app, resource, startTime, endTime);
        List<MetricVo> vos = MetricVo.fromMetricEntities(entities, resource);
        Iterable<MetricVo> vosSorted = sortMetricVoAndDistinct(vos);
        //查询分页数据
        Page<MetricEntity> page = new Page<>(pageIndex, pageSize);
        wrapper.orderBy("TIME_STAMP", false);
        List<MetricEntity> list = repository.selectPage(page, wrapper);
        //计算分页
//        int startIndex = pageIndex * pageSize == 0 ? 0 : pageIndex * pageSize - 1;
        List<MetricVo> vo1 = MetricVo.fromMetricEntities(list, resource);

        Map<String, Object> resultMap = new LinkedHashMap<>();
        int totalPage = (totalCount + pageSize - 1) / pageSize;
        resultMap.put("metric", vosSorted);
        resultMap.put("metricPage", vo1);
        resultMap.put("totalCount", totalCount);
        resultMap.put("totalPage", totalPage);
        resultMap.put("pageIndex", pageIndex);
        resultMap.put("pageSize", pageSize);
        return Result.ofSuccess(resultMap);
    }


}
