package com.eshop.inventory.manage.common.enums;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: EhcacheTypeEnum
 * @projectName inventory
 * @date 2019/6/15 22:17
 */
public enum  EhcacheTypeEnum {
    ITEM("EHCACHE:ITEM:","商品信息")
    ;


    private String name;

    private String msg;


    EhcacheTypeEnum(String name, String msg) {
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
    public EhcacheTypeEnum getEnumByName(String name){
        for (EhcacheTypeEnum  e:EhcacheTypeEnum.values() ){
            if(e.name.equals(name) ){
                return e;
            }
        }
        return null;
    }

}
