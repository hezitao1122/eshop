package com.eshopinventory.inventory.common.dto;


import com.eshopinventory.inventory.config.exception.MyException;
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
    private List<T> datas;

    public ResultDto() {
    }

    public static <T> ResultDto create(){
        return new ResultDto<T>();
    }
    /**
     * 用于返回结果的方法,带data
     *
     * @param data 返回结果集
     */
    public ResultDto(T data) {
        this.data = data;
        create(ResultEnum.SUCCESS,data);
    }
    /**
     * 用于返回结果的方法,带data
     *
     * @param datas 结果
     */
    public static<T> ResultDto create( List<T> datas) {
        return new ResultDto( datas);
    }
    /**
     * 用于返回结果的方法,带data
     *
     * @param datas 返回结果集
     */
    public ResultDto(List<T> datas) {
        this.datas = datas;
        create(ResultEnum.SUCCESS,datas);
    }
    /**
     * 用于返回结果的方法,带data
     *
     * @param data 结果
     */
    public static<T> ResultDto create( T data) {
        return new ResultDto( data);
    }

    /**
     * 用于返回结果的方法,带data
     *
     * @param resultEnum 结果枚举
     * @param t
     */
    private ResultDto(ResultEnum resultEnum, T t) {
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
        this.data = t;
    }

    /**
     * 用于返回结果的方法,带data
     *
     * @param resultEnum 结果枚举
     * @param t
     */
    public static<T> ResultDto create(ResultEnum resultEnum, T t) {
        return new ResultDto(resultEnum, t);
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
     * 用于返回结果的方法,不带data
     *
     * @param resultEnum 结果枚举
     */
    public static ResultDto create(ResultEnum resultEnum) {
        return new ResultDto(resultEnum);
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
     * @Description: 发生异常的构造方法
     * @Params: MyException
     * @Author: hzt
     * @Date: 2018/9/6 11:45
     */
    public static ResultDto create(MyException e) {
        return new ResultDto(e);
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
     */
    public static ResultDto create(Integer code, String msg) {
        return new ResultDto(code, msg);
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

    /**
     * 不通过枚举进行返回的方法,不带参数
     *
     * @param code 返回码
     * @param msg  返回提示信息
     * @param data 返回体
     */
    public static<T> ResultDto create(Integer code, String msg, T data) {
        return new ResultDto(code, msg, data);
    }


}
