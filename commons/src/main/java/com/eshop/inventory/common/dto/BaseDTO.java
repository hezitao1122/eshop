package com.eshop.inventory.common.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author zeryts
 * @description: 公共的DTO实体类
 * ```````````````````````````
 * @title: BaseDTO
 * @projectName inventory
 * @date 2019/6/12 22:30
 */
public abstract class BaseDTO<T> {

    public T parseEntity() {
        String s = JSON.toJSONString(this);
        JSONObject jsonObject = JSON.parseObject(s);
        return (T) jsonObject;
    }
}
