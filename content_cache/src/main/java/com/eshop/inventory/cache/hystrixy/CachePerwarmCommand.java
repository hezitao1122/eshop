package com.eshop.inventory.cache.hystrixy;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;

/**
 * @author zeryts
 * @description: hystrix的资源隔离,对这个依赖的所有条用请求,全部走这个资源池内的资源,不会去用其他的资源,这个叫资源隔离
 * 此类为hystrixy的最最基本的资源隔离技术,线程池隔离技术
 * 对某一个依赖服务,商品服务,所有的调用请求,全部隔离到一个线程池内,对商品服务的每次调用请求都封装到一个command里面
 * 每个command(每次服务调用请求)都是使用线程池的一个线程去执行的
 * 所以哪怕是对这个依赖服务,商品服务同时发起的调用已经达到了1000,但是线程池也就使用10个线程,最多就只会用这10个线程去执行
 * 不会说,对商品服务的请求,因为接口的调用延迟,将tomcat内部所有的线程资源全部耗尽
 * -----------------------------------
 * @title: CachePerwarmCommand
 * @projectName inventory
 * @date 2020/5/10 16:47
 */
public class CachePerwarmCommand extends HystrixObservableCommand<String> {

    private String name;
    /** description: 初始化构造方法
     * @param name 构造传入的名称
     * @Author: zeryts
     * @email: hezitao@agree.com
     * @Date: 2020/5/10 18:46
     */
    public CachePerwarmCommand(String name){
        super(HystrixCommandGroupKey.Factory.asKey(name));
        this.name = name;
    }

    @Override
    protected Observable<String> construct() {
        return null;
    }
}
