package com.eshop.inventory.common.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.eshop.inventory.common.entity.BaseEntity;

import java.lang.reflect.Type;

/**
 * @author zeryts
 * @description: 公共的DTO实体类
 * ```````````````````````````
 * @title: BaseDTO
 * @projectName inventory
 * @date 2019/6/12 22:30
 */
public class BaseDTO<E, D extends BaseDTO> {
    /**
     * 功能描述: 拷贝类的方法<br>
     * 〈〉
     * @param entity entity类
     * @return: D 目标类
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/12 23:10
     */
    public <E> D parseDTO(E entity) {
        Class<?> c = this.getClass();
        String str = JSON.toJSONString(entity);
        D d = JSONObject.parseObject(str, (Type) c);
        return d;
    }

}
