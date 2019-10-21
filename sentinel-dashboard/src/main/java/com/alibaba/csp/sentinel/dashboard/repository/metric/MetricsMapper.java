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
package com.alibaba.csp.sentinel.dashboard.repository.metric;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.MetricEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 保存至数据库的repository
 * @author zeryts
 */
public interface MetricsMapper extends BaseMapper<MetricEntity> {

    /** description: 查找所有存储的APP信息
     * @return: java.util.List<java.lang.String>
     * @Author: zeryts
     * @Date: 2019/9/3 18:57
     */
    @Select("SELECT DISTINCT APP FROM SYS_GATEWAY_LOG ")
    List<String> selectAllApp();

    /** description: 根据APP查询所有的资源信息
     * @param app 名称
     * @return: java.util.List<java.lang.String>
     * @Author: zeryts
     * @Date: 2019/9/5 14:53
     */
    @Select("SELECT RESOURCE_ID  from SYS_GATEWAY_LOG where APP = #{app} GROUP BY RESOURCE_ID ORDER BY SUM(PASS_QPS) DESC")
    List<String> queryResourcesByAppOrderByPassQps(@Param("app") String app);
}
