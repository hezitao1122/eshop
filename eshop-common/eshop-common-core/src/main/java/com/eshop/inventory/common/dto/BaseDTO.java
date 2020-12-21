package com.eshop.inventory.common.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author zeryts
 * @description: 公共的DTO实体类
 * ```````````````````````````
 * @title: BaseDTO
 * @projectName inventory
 * @date 2019/6/12 22:30
 */
@Data
public class BaseDTO<T> {

    public T parseEntity() {
        String s = JSON.toJSONString(this);
        JSONObject jsonObject = JSON.parseObject(s);
        return (T) jsonObject;
    }
}
