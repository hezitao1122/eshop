package com.eshop.inventory.storm.vo;

/**
 * @author zeryts
 * @description: kafka操作的vo类
 * ```````````````````````````
 * @title: KafkaBaseVo
 * @projectName inventory
 * @date 2019/6/18 21:43
 */
public abstract class KafkaBaseVo {
    private String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
