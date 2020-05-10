package com.eshop.inventory.common.enums;

/**
 * @author zeryts
 * @description: 排序的根据枚举
 * -----------------------------------
 * @title: SortEnum
 * @projectName inventory
 * @date 2020/3/3 16:11
 */
public enum SortEnum {
    ASC(0,"顺序"),
    DESC(1,"倒叙")
    ;
    private Integer type;

    private String value;

    SortEnum(Integer type,String value ){
        this.type = type;
        this.value = value;
    }


}
