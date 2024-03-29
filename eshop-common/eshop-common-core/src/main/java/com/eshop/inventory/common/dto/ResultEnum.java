package com.eshop.inventory.common.dto;

/**
 * @author: zeryts
 * @date: 2018-09-03 21:58
 * @version: 1.0
 * @description:
 */
public enum ResultEnum {
    ERROR(0, "服务器异常!"),
    SUCCESS(1, "请求成功!"),
    FEIGN_ERROR(2, "远程调用异常!"),
    BUSINESS_EXCEPTION(3, "业务异常!");

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
