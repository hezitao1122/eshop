package com.eshop.inventory.cache.hystrixy;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.feign.ItemDescFeign;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zeryts
 * @description: hystrix的资源隔离, 对这个依赖的所有条用请求, 全部走这个资源池内的资源, 不会去用其他的资源, 这个叫资源隔离
 * 此类为hystrixy的最最基本的资源隔离技术,线程池隔离技术
 * 对某一个依赖服务,商品服务,所有的调用请求,全部隔离到一个线程池内,对商品服务的每次调用请求都封装到一个command里面
 * 每个command(每次服务调用请求)都是使用线程池的一个线程去执行的
 * 所以哪怕是对这个依赖服务,商品服务同时发起的调用已经达到了1000,但是线程池也就使用10个线程,最多就只会用这10个线程去执行
 * 不会说,对商品服务的请求,因为接口的调用延迟,将tomcat内部所有的线程资源全部耗尽
 *
 * 有4种方式可以执行一个Hystrix命令。
 * 1. execute()—该方法是阻塞的，从依赖请求中接收到单个响应（或者出错时抛出异常）。
 * 2. queue()—从依赖请求中返回一个包含单个响应的Future对象。
 * 3. observe()—订阅一个从依赖请求中返回的代表响应的Observable对象。
 * 4. toObservable()—返回一个Observable对象，只有当你订阅它时，它才会执行Hystrix命令并发射响应。
 *
 * HystrixCommand 查询的为单条数据
 * HystrixObservableCommand 查询的为多条数据
 *
 *
 * -----------------------------------
 * @title: ItemDescCachePerwarmCommand
 * @projectName inventory
 * @date 2020/5/10 16:47
 */
public class ItemDescCachePerwarmCommand extends HystrixCommand<TbItemDescDTO> {

    private Long id;

    @Autowired
    private ItemDescFeign itemDescFeign;

    public ItemDescCachePerwarmCommand(Long id){
        //绑定的线程池  所有请求都用此线程池
        super(HystrixCommandGroupKey.Factory.asKey("ItemCachePerwarmCommandGroup"));
        //传入的构造值
        this.id = id;
    }

    @Override
    protected TbItemDescDTO run() throws Exception {
        //进行查询
        ResultDto<TbItemDescDTO> dto = itemDescFeign.find(id);
        return dto.getData();
    }
}
