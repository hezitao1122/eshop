package com.eshop.inventory.config.web;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Arrays;

/**
 * @description: 用于打印请求和返回日志的AOP配置类
 * @ClassName: ControllerConfig
 * @author: zeryts
 * @date: 2018-11-30 13:05
 */
@Aspect
@Component
public class ControllerConfig {
    private Logger log = LoggerFactory.getLogger(getClass().getName());

    @Pointcut(value = "execution(public * com.eshop.inventory.*.*.controller.*.*(..) )")
    private void point() {
    }

    @Pointcut(value = "execution(public * com.eshop.inventory.common.base.impl.*Controller.*(..) )")
    private void pointBase() {
    }


    @Around(value = "point() || pointBase()")
    public Object pointcut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        //参数列表
        Object[] args = proceedingJoinPoint.getArgs();

        StringBuilder sb = new StringBuilder();
        Arrays.asList(args).forEach((o) -> {
            if (!(o instanceof ServletRequest) && !(o instanceof MultipartFile) && !(o instanceof ServletResponse)) {
                sb.append(JSON.toJSONString(o) + ",");
            }
        });
        String content = sb.toString();
        if (sb.length() > 0) content = sb.substring(0, sb.length() - 1);


        log.info("执行->[{}]类的->[{}]方法的请求参数为:->[{}]", className, methodName, content);
        Object proceed = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();


        log.info("执行->[{}]类的[{}]方法的返回报文为:->[{}],执行时长为:[{}] ms", className, methodName, JSON.toJSONString(proceed), (endTime - startTime));
        return proceed;
    }
}
