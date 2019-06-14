package com.eshop.inventory.manage.common;

import com.eshop.inventory.manage.item.enums.TypeEnum;

/**
 * @author zeryts
 * @description: 公共缓存的枚举类
 * ```````````````````````````
 * @title: CacheEnum
 * @projectName inventory
 * @date 2019/6/1223:36
 */
public enum CacheEnum {

    CACHE_COMMON_PRE(TypeEnum.CACHE_PREFIX_NAME + "ITEM:","商品缓存的前缀")

    ;
    private String code;

    private String msg;

    CacheEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
