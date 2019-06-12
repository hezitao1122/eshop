package com.eshop.inventory.config.exception;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.dto.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description: 用于统一异常处理的类
 * @author: hzt
 * @date: 2018-09-05 19:53
 */
@ControllerAdvice
public class MyExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * @Description: 如果是MyException的异常
     * @Params: MyException.class
     * @Author: hzt
     * @Date: 2018/9/6 11:46
     * @Return: ResultDto 转化json的异常结果集
     */
    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ResultDto error(MyException e) {
        return new ResultDto(e);
    }

    /**
     * @param e 异常对象
     * @Description: 其他异常的异常处理机制
     * @Author: hzt
     * @Date: 2018/9/6 16:52
     * @Return: com.slp.cms.common.vo.ResultDto
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultDto error(Exception e) {
        log.info(e.toString(), e);
        return new ResultDto(ResultEnum.ERROR);
    }
}
