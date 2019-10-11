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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.ResourceReqEntity;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.csp.sentinel.command.vo.NodeVo;

import com.alibaba.csp.sentinel.dashboard.domain.ResourceTreeNode;
import com.alibaba.csp.sentinel.dashboard.client.SentinelApiClient;
import com.alibaba.csp.sentinel.dashboard.domain.Result;
import com.alibaba.csp.sentinel.dashboard.domain.vo.ResourceVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Carpenter Lee
 */
@RestController
@RequestMapping(value = "/resource")
public class ResourceController {

    private static Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private SentinelApiClient httpFetcher;

    /**
     * Fetch real time statistics info of the machine.
     *
     * @param entity        ip to fetch
     * @param entity      port of the ip
     * @param entity      one of [root, default, cluster], 'root' means fetching from tree root node, 'default' means
     *                  fetching from tree default node, 'cluster' means fetching from cluster node.
     * @param entity key to search
     * @return node statistics info.
     */
    @PostMapping("/machineResource.json")
    public Result<Object> fetchResourceChainListOfMachine(@RequestBody ResourceReqEntity entity) {
        String ip = entity.getIp();
        Integer port = entity.getPort();
        String searchKey = entity.getSearchKey();
        String type = entity.getType();
        Integer pageIndex = entity.getPage();
        Integer pageSize = entity.getLimit();

        if (pageIndex == null || pageIndex <= 0) {
            pageIndex = 1;
        }
        if (pageSize == null) {
            pageSize = 6;
        }
        if (pageSize >= 20) {
            pageSize = 20;
        }

        if (StringUtil.isEmpty(ip) || port == null) {
            return Result.ofFail(-1, "invalid param, give ip, port");
        }
        final String ROOT = "root";
        final String DEFAULT = "default";
        if (StringUtil.isEmpty(type)) {
            type = ROOT;
        }
        if (ROOT.equalsIgnoreCase(type) || DEFAULT.equalsIgnoreCase(type)) {
            List<NodeVo> nodeVos = httpFetcher.fetchResourceOfMachine(ip, port, type);
            if (nodeVos == null) {
                return Result.ofSuccess(null);
            }
            ResourceTreeNode treeNode = ResourceTreeNode.fromNodeVoList(nodeVos);
            treeNode.searchIgnoreCase(searchKey);
            return Result.ofSuccess(ResourceVo.fromResourceTreeNode(treeNode));
        } else {
            // Normal (cluster node).
            List<NodeVo> nodeVos = httpFetcher.fetchClusterNodeOfMachine(ip, port, true);
            if (nodeVos == null) {
                return Result.ofSuccess(null);
            }
            if (StringUtil.isNotEmpty(searchKey)) {
                nodeVos = nodeVos.stream().filter(node -> node.getResource()
                    .toLowerCase().contains(searchKey.toLowerCase()))
                    .collect(Collectors.toList());
            }
            int totalCount = nodeVos.size();
            int totalPage = (nodeVos.size() + pageSize - 1) / pageSize;
            if (pageIndex <= totalPage) {
                nodeVos = nodeVos.subList((pageIndex - 1) * pageSize,
                        Math.min(pageIndex * pageSize, nodeVos.size()));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("totalCount",totalCount);
            map.put("totalPage", totalPage);
            map.put("page", pageIndex);
            map.put("limit", pageSize);
            map.put("nodes",ResourceVo.fromNodeVoList(nodeVos));
            return Result.ofSuccess(map);
        }
    }
}
