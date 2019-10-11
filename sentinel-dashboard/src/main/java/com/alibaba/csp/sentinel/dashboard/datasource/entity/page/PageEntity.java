package com.alibaba.csp.sentinel.dashboard.datasource.entity.page;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;

/**
 * @author zeryts
 * @description: TODO
 * -----------------------------------
 * @title: PageEntity
 * @projectName amap
 * @date 2019/9/24 11:18
 */
public class PageEntity<T extends RuleEntity>  {
    private Long id;
    /**
     * app名称
     */
    private String app;
    /**
     * IP地址
     */
    private String ip;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 默认为 default
     */
    private String limitApp;

    private Integer page;
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLimitApp() {
        return limitApp;
    }

    public void setLimitApp(String limitApp) {
        this.limitApp = limitApp;
    }
}
