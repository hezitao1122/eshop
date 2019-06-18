package com.eshop.inventory.common.dto;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.common.entity.BaseEntity;
import lombok.Data;

/**
 * @author zeryts
 * @description: kafka操作的vo类
 * ```````````````````````````
 * @title: KafkaBaseVo
 * @projectName inventory
 * @date 2019/6/18 21:43
 */
@Data
public abstract class KafkaBaseVo {
    private String serviceId;



    public BaseEntity parseEntity(Class<? extends BaseEntity> clazz){
        String s = JSON.toJSONString(this);
        BaseEntity baseEntity = JSON.parseObject(s, clazz);
        return  baseEntity;
    }
}
