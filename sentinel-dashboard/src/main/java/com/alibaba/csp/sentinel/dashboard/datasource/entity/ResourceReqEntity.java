package com.alibaba.csp.sentinel.dashboard.datasource.entity;

/**
 * @author zeryts
 * @description: 资源操作相关的接收参数
 * -----------------------------------
 * @title: ResourceReqEntity
 * @projectName amap
 * @date 2019/9/6 11:11
 */
public class ResourceReqEntity {

    private String ip;

    private Integer port;

    private String searchKey;

    private String type;

    private Integer page;
    private Integer limit;


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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
}
