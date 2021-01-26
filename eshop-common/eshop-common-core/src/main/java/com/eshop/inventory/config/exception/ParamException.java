package com.eshop.inventory.config.exception;

/**
 * @author: zeryts
 * @date: 2018-11-04 15:53
 * @version: 1.0
 * @description: 参数定义的异常
 */
public class ParamException extends RuntimeException {
    public ParamException(String message) {
        super(message);
    }
}
