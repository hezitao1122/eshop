package com.eshop.inventory.common.dto;


import com.eshop.inventory.config.exception.MyException;
import lombok.Data;

import java.util.List;

/**
 * @author: hzt
 * @date: 2018-09-03 21:57
 * @version: 1.0
 * @description: 返回实体
 */
@Data
public class ResultDto<T> {

    private Integer code;

    private String msg;

    private T data;
    private List<T> dataLis;

    public ResultDto() {
        this.code = ResultEnum.SUCCESS.getCode();
        this.msg = ResultEnum.SUCCESS.getMsg();
    }

    /**
     * 用于返回结果的方法,带data
     *
     * @param data 返回结果集
     */
    public ResultDto(T data) {
        this(ResultEnum.SUCCESS,data);
    }
    /**
     * 用于返回结果的方法,带data
     *
     * @param dataLis 返回结果集
     */
    public ResultDto(List<T> dataLis) {
        this(ResultEnum.SUCCESS,dataLis);
    }

    /**
     * 用于返回结果的方法,带data
     *
     * @param dataLis 返回结果集
     */
    public ResultDto(ResultEnum resultEnum,List<T> dataLis) {
        this.dataLis = dataLis;
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
    }

    /**
     * 用于返回结果的方法,带data
     *
     * @param resultEnum 结果枚举
     * @param t
     */
    private ResultDto(ResultEnum resultEnum, T t) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = t;
    }

    /**
     * 用于返回结果的方法,不带data
     *
     * @param resultEnum 结果枚举
     */
    private ResultDto(ResultEnum resultEnum) {
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
    }
    /**
     * @Description: 发生异常的构造方法
     * @Params: MyException
     * @Author: hzt
     * @Date: 2018/9/6 11:45
     */
    private ResultDto(MyException e) {
        code = e.getCode();
        msg = e.getMsg();
    }
    /**
     * 不通过枚举进行返回的方法,不带参数
     *
     * @param code 返回码
     * @param msg  返回提示信息
     */
    private ResultDto(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 不通过枚举进行返回的方法,不带参数
     *
     * @param code 返回码
     * @param msg  返回提示信息
     * @param data 返回体
     */
    private ResultDto(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

}
