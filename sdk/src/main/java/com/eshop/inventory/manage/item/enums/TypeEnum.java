package com.eshop.inventory.manage.item.enums;

/**
 * @author zeryts
 * @description: 用语存放商品类型的enum
 * ```````````````````````````
 * @title: TypeEnum
 * @projectName inventory
 * @date 2019/6/12 23:35
 */
public enum  TypeEnum {
    CACHE_PREFIX_NAME("LOAD:CACHE","缓存的前缀")
    ;
    private String code;

    private String msg;

    TypeEnum(String code, String msg) {
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
