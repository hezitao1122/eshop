package com.eshop.inventory.common.enums;

/**
 * @author zeryts
 * @description: Redis操作的枚举类
 * ```````````````````````````
 * @title: RedisOperateEnums
 * @projectName inventory
 * @date 2020/6/616:22
 */
public enum RedisOperateEnums {
    ADD(1,"增加"),
    DELETE(2,"删除"),
    SELECT(3,"查询"),
    ;

    private Integer code;

    private String msg;

    RedisOperateEnums(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }




}
