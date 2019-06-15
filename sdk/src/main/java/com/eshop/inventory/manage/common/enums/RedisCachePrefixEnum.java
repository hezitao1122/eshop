package com.eshop.inventory.manage.common.enums;

/**
 * @author zeryts
 * @description: redis存储前缀的枚举类
 * ```````````````````````````
 * @title: RedisCachePrefixEnum
 * @projectName inventory
 * @date 2019/5/3023:42
 */
public enum RedisCachePrefixEnum {
    ITEM_NUM("INVENTORY:ITEM:NUM:","商品库存信息")

    ;

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
     * @return: com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum
     * @since: 1.0.0
     * @author zeryts
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
