package com.eshop.inventory.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @description: redis的参数配置类
 * ```````````````````````````
 * @title: RedisProperties
 * @projectName inventory
 * @date 2019/5/2922:09
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class RedisProperties {

    private String nodes;
    private Integer commandTimeout;
    private Integer maxAttempts;
    private Integer maxRedirects;
    private Integer maxActive;
    private Integer maxWait;
    private Integer maxIdle;
    private Integer minIdle;
    private boolean testOnBorrow;

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public Integer getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(Integer commandTimeout) {
        this.commandTimeout = commandTimeout;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public Integer getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(Integer maxRedirects) {
        this.maxRedirects = maxRedirects;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }
}
