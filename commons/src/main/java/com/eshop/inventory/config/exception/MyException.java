package com.eshop.inventory.config.exception;

import com.eshop.inventory.common.dto.ResultEnum;

/**
 * @description: 用于json处理的异常
 * @author: hzt
 * @date: 2018-09-06 11:38
 */
public class MyException extends RuntimeException {
    private Integer code;
    private String msg;

    public MyException() {
    }

    public MyException(ResultEnum resultEnum) {
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
    }

    public MyException(String msg) {
        this.code = 3;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
