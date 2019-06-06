package com.eshop.inventory.common.enums;

/**
 * @author Administrator
 * @description: redis存储前缀的枚举类
 * ```````````````````````````
 * @title: RedisCachePrefixEnum
 * @projectName inventory
 * @date 2019/5/3023:42
 */
public enum RedisCachePrefixEnum {
    ITEM("item:","商品信息")
    ;

    private static final String prefix = "inventory:";

    private String name;

    private String msg;

    RedisCachePrefixEnum(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public String getMsg() {
        return msg;
    }
    /**
     * 功能描述: 根据名称获取枚举对象<br>
     * 〈〉
     *
     * @param name 枚举的name值
     * @return: com.eshop.inventory.common.enums.RedisCachePrefixEnum
     * @since: 1.0.0
     * @Author: Administrator
     * @Date: 2019/5/30 23:47
     */
    public RedisCachePrefixEnum getEnumByName(String name){
        for (RedisCachePrefixEnum  e:RedisCachePrefixEnum.values() ){
            if(e.name.equals(name) ){
                return e;
            }
        }
        return null;
    }



}
