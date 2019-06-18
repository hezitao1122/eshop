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
    ITEM_NUM("INVENTORY:ITEM:NUM:","商品库存信息"),
    ITEM("INVENTORY:ITEM:","商品信息"),
    ITEM_CART("INVENTORY:ITEM_CART:","商品类目"),
    ITEM_DESC("INVENTORY:ITEM_DESC:","商品描述"),
    ITEM_PARAM("INVENTORY:ITEM_PARAM:","商品参数"),
    ITEM_PARAM_ITEM("INVENTORY:ITEM_PARAM_ITEM:","商品规格和商品的关系操作"),
    CONTENT("INVENTORY:CONTENT:","内容"),
    CONTENT_CATEGORY("INVENTORY:CATEGORY:","内容分类"),
    ORDER("INVENTORY:ORDER:","订单"),
    ORDER_ITEM("INVENTORY:ORDER_ITEM:","订单商品"),
    ORDER_SHOPPING("INVENTORY:ORDER_SHOPPING:","购物车"),
    USER("INVENTORY:USER:","用户"),

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
