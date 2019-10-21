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
package com.alibaba.csp.sentinel.dashboard.domain.cluster;

import java.util.Set;

import com.alibaba.csp.sentinel.dashboard.domain.cluster.request.ClusterAppAssignMap;

/**
 * @author Eric Zhao
 * @since 1.4.1
 */
public class ClusterAppSingleServerAssignRequest {

    private ClusterAppAssignMap clusterMap;
    private Set<String> remainingList;

    private String app;

    public ClusterAppAssignMap getClusterMap() {
        return clusterMap;
    }

    public ClusterAppSingleServerAssignRequest setClusterMap(ClusterAppAssignMap clusterMap) {
        this.clusterMap = clusterMap;
        return this;
    }

    public Set<String> getRemainingList() {
        return remainingList;
    }

    public ClusterAppSingleServerAssignRequest setRemainingList(Set<String> remainingList) {
        this.remainingList = remainingList;
        return this;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public String toString() {
        return "ClusterAppSingleServerAssignRequest{" +
            "clusterMap=" + clusterMap +
            ", remainingList=" + remainingList +
            '}';
    }
}
