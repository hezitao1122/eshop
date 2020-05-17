package com.eshop.inventory.hystrixy.filter;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author zeryts
 * @description: 请求缓存技术 , 基于request-context请求上下文
 * 在每个请求的filter对每个Command请求施加上下文,当多个产生Command request时,当参数一致的情况下
 * 默认其返回的结果也为一致的,即缓存第一次,然后后面的都返回内存结果
 * ```````````````````````````
 * @title: HystrixRequestContextFilter
 * @projectName inventory
 * @date 2020/5/16 11:32
 */
public class HystrixRequestContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /**
         * 初始化一个Hystrix的上下文
         */
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try{
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            /**
             * 关闭上下文
             */
            context.shutdown();
        }


    }
}
