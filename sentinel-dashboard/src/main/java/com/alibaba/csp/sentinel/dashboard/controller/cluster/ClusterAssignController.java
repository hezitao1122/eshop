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
package com.alibaba.csp.sentinel.dashboard.controller.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.eshop.inventory.common.enums.RuleConsts;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;
import com.alibaba.csp.sentinel.util.StringUtil;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterAppFullAssignRequest;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterAppAssignResultVO;
import com.alibaba.csp.sentinel.dashboard.domain.cluster.ClusterAppSingleServerAssignRequest;
import com.alibaba.csp.sentinel.dashboard.service.ClusterAssignService;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
@RestController
@RequestMapping("/cluster/assign")
public class ClusterAssignController {

    private final Logger logger = LoggerFactory.getLogger(ClusterAssignController.class);

    @Autowired
    private ClusterAssignService clusterAssignService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/all_server/{app}")
    public Result<ClusterAppAssignResultVO> apiAssignAllClusterServersOfApp(@PathVariable String app,
                                                                            @RequestBody
                                                                                    ClusterAppFullAssignRequest assignRequest) {
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app cannot be null or empty");
        }
        if (assignRequest == null || assignRequest.getClusterMap() == null
                || assignRequest.getRemainingList() == null) {
            return Result.ofFail(-1, "bad request body");
        }
        String key = app + RuleConsts.CLUSTER_MAP_DATA_ID;
        String value = redisTemplate.opsForValue().get(key);
        try {
            //放到redis的key
            redisTemplate.opsForValue().set(key, JSON.toJSONString(assignRequest.getClusterMap()));
            Result<ClusterAppAssignResultVO> res = Result.ofSuccess(clusterAssignService.applyAssignToApp(app, assignRequest.getClusterMap(),
                    assignRequest.getRemainingList()));
            redisTemplate.convertAndSend(app+RuleConsts.GROUPID, JSON.toJSONString(assignRequest.getClusterMap()));
            //放到数据库中


            return res;
        } catch (Throwable throwable) {
            logger.error("Error when assigning full cluster servers for app: " + app, throwable);
            //失败,删除
            redisTemplate.opsForValue().set(key, value);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }

    @PostMapping("/single_server/{app}")
    public Result<ClusterAppAssignResultVO> apiAssignSingleClusterServersOfApp(@PathVariable String app,
                                                                               @RequestBody ClusterAppSingleServerAssignRequest assignRequest) {
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app cannot be null or empty");
        }
        if (assignRequest == null || assignRequest.getClusterMap() == null) {
            return Result.ofFail(-1, "bad request body");
        }
        String key = app + RuleConsts.CLUSTER_MAP_DATA_ID;
        String value = redisTemplate.opsForValue().get(key);
        try {
            //查找对应的app
            List<ClusterAppAssignMap> clusterAppFullAssignRequest = JSON.parseArray(value, ClusterAppAssignMap.class);
            if(clusterAppFullAssignRequest != null && clusterAppFullAssignRequest.size()> 0 ){
                for (int i = 0; i < clusterAppFullAssignRequest.size(); i++) {
                    if (clusterAppFullAssignRequest.get(i).getMachineId().equals(assignRequest.getClusterMap().getMachineId())) {
                        clusterAppFullAssignRequest.set(i,assignRequest.getClusterMap());
                        break;
                    }
                }
            }else{
                ArrayList<ClusterAppAssignMap> objects = new ArrayList<>();
                objects.add(assignRequest.getClusterMap());
                clusterAppFullAssignRequest = objects;
            }

            redisTemplate.opsForValue().set(key, JSON.toJSONString(clusterAppFullAssignRequest));
            Result<ClusterAppAssignResultVO> clusterAppAssignResultVOResult = Result.ofSuccess(
                    clusterAssignService.applyAssignToApp(app, Collections.singletonList(assignRequest.getClusterMap()),
                            assignRequest.getRemainingList()));
            redisTemplate.convertAndSend(app+RuleConsts.GROUPID, JSON.toJSONString(clusterAppFullAssignRequest));
            return clusterAppAssignResultVOResult;
        } catch (Throwable throwable) {
            logger.error("Error when assigning single cluster servers for app: " + app, throwable);
            //失败,删除
            redisTemplate.opsForValue().set(key, value);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }

    @PostMapping("/single_server")
    public Result<ClusterAppAssignResultVO> apiAssignSingleClusterServersOfApp(
            @RequestBody ClusterAppSingleServerAssignRequest assignRequest) {
        String app = assignRequest.getApp();
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app cannot be null or empty");
        }
        if (assignRequest == null || assignRequest.getClusterMap() == null) {
            return Result.ofFail(-1, "bad request body");
        }
        String key = app + RuleConsts.CLUSTER_MAP_DATA_ID;
        String value = redisTemplate.opsForValue().get(key);
        try {
            //查找对应的app
            List<ClusterAppAssignMap> clusterAppFullAssignRequest = JSON.parseArray(value, ClusterAppAssignMap.class);
            if(clusterAppFullAssignRequest != null && clusterAppFullAssignRequest.size()> 0 ){
                for (int i = 0; i < clusterAppFullAssignRequest.size(); i++) {
                    if (clusterAppFullAssignRequest.get(i).getMachineId().equals(assignRequest.getClusterMap().getMachineId())) {
                        clusterAppFullAssignRequest.set(i,assignRequest.getClusterMap());
                        break;
                    }
                }
            }else{
                ArrayList<ClusterAppAssignMap> objects = new ArrayList<>();
                objects.add(assignRequest.getClusterMap());
                clusterAppFullAssignRequest = objects;
            }

            redisTemplate.opsForValue().set(key, JSON.toJSONString(clusterAppFullAssignRequest));
            Result<ClusterAppAssignResultVO> clusterAppAssignResultVOResult = Result.ofSuccess(
                    clusterAssignService.applyAssignToApp(app, Collections.singletonList(assignRequest.getClusterMap()),
                            assignRequest.getRemainingList()));
            redisTemplate.convertAndSend(app+RuleConsts.GROUPID, JSON.toJSONString(clusterAppFullAssignRequest));
            return clusterAppAssignResultVOResult;
        } catch (Throwable throwable) {
            logger.error("Error when assigning single cluster servers for app: " + app, throwable);
            //失败,删除
            redisTemplate.opsForValue().set(key, value);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }

    @PostMapping("/unbind_server/{app}")
    public Result<ClusterAppAssignResultVO> apiUnbindClusterServersOfApp(@PathVariable String app,
                                                                         @RequestBody Set<String> machineIds) {
        if (StringUtil.isEmpty(app)) {
            return Result.ofFail(-1, "app cannot be null or empty");
        }
        if (machineIds == null || machineIds.isEmpty()) {
            return Result.ofFail(-1, "bad request body");
        }
        String key = app + RuleConsts.CLUSTER_MAP_DATA_ID;
        String value = redisTemplate.opsForValue().get(key);
        try {
            Result<ClusterAppAssignResultVO> res = Result.ofSuccess(clusterAssignService.unbindClusterServers(app, machineIds));
            if(res.isSuccess()){
                redisTemplate.opsForValue().set(key,"[]");
                redisTemplate.convertAndSend(app+RuleConsts.GROUPID, "[]");
            }
            return res;
        } catch (Throwable throwable) {
            logger.error("Error when unbinding cluster server {} for app <{}>", machineIds, app, throwable);
            redisTemplate.opsForValue().set(key,value);
            return Result.ofFail(-1, throwable.getMessage());
        }
    }
}
