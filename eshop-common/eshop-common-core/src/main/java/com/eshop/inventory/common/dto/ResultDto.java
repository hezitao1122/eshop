package com.eshop.inventory.common.dto;


import com.eshop.inventory.config.exception.MyException;

import java.util.List;

/**
 * @author: zeryts
 * @date: 2018-09-03 21:57
 * @version: 1.0
 * @description: 返回实体
 */
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
        this(ResultEnum.SUCCESS, data);
    }

    /**
     * 用于返回结果的方法,带data
     *
     * @param dataLis 返回结果集
     */
    public ResultDto(List<T> dataLis) {
        this(ResultEnum.SUCCESS, dataLis);
    }

    /**
     * 用于返回结果的方法,带data
     *
     * @param dataLis 返回结果集
     */
    public ResultDto(ResultEnum resultEnum, List<T> dataLis) {
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
    public ResultDto(ResultEnum resultEnum, T t) {
        this.code = resultEnum.getCode();
        this.msg = resultEnum.getMsg();
        this.data = t;
    }

    /**
     * 用于返回结果的方法,不带data
     *
     * @param resultEnum 结果枚举
     */
    public ResultDto(ResultEnum resultEnum) {
        code = resultEnum.getCode();
        msg = resultEnum.getMsg();
    }

    /**
     * 用于返回结果的方法,不带data
     *
     * @param resultEnum 结果枚举
     */
    private ResultDto(ResultEnum resultEnum, String msg) {
        this.code = resultEnum.getCode();
        this.msg = msg;
    }


    /**
     * @Description: 发生异常的构造方法
     * @Params: MyException
     * @Author: zeryts
     * @Date: 2018/9/6 11:45
     */
    public ResultDto(MyException e) {
        this.code = e.getCode();
        this.msg = e.getMsg();
    }

    /**
     * 不通过枚举进行返回的方法,不带参数
     *
     * @param code 返回码
     * @param msg  返回提示信息
     */
    public ResultDto(Integer code, String msg) {
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
    public ResultDto(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public List<T> getDataLis() {
        return this.dataLis;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setDataLis(List<T> dataLis) {
        this.dataLis = dataLis;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ResultDto)) return false;
        final ResultDto<?> other = (ResultDto<?>) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$msg = this.getMsg();
        final Object other$msg = other.getMsg();
        if (this$msg == null ? other$msg != null : !this$msg.equals(other$msg)) return false;
        final Object this$data = this.getData();
        final Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
        final Object this$dataLis = this.getDataLis();
        final Object other$dataLis = other.getDataLis();
        if (this$dataLis == null ? other$dataLis != null : !this$dataLis.equals(other$dataLis)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResultDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $msg = this.getMsg();
        result = result * PRIME + ($msg == null ? 43 : $msg.hashCode());
        final Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        final Object $dataLis = this.getDataLis();
        result = result * PRIME + ($dataLis == null ? 43 : $dataLis.hashCode());
        return result;
    }

    public String toString() {
        return "ResultDto(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ", dataLis=" + this.getDataLis() + ")";
    }
}
