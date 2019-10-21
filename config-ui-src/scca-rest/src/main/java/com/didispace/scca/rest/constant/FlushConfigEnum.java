package com.didispace.scca.rest.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zeryts
 * @description: 是否更改后刷新项目的枚举
 * -----------------------------------
 * @title: FlushConfigEnum
 * @projectName amap
 * @date 2019/9/26 16:29
 */
public enum FlushConfigEnum {
    NO_FLUSH(1, "NO FLUSH"),
    FLUSH(2, "FLUSH");

    private Integer key;

    private String value;
    private static Map<Integer, String> enumMap = new HashMap<>();

    static {
        for (FlushConfigEnum userRoleEnum : FlushConfigEnum.values()) {
            enumMap.put(userRoleEnum.key, userRoleEnum.value);
        }
    }

    FlushConfigEnum(Integer key, String value){
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(Integer key){
        String value = enumMap.get(key);
        return value != null ? value : "";
    }


}
