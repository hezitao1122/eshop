package com.eshop.inventory.cache.hystrixy;

import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.feign.ItemFeign;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zeryts
 * @description: 用语查询多条数据的Command
 *  HystrixCommand :是用来获取一条数据的Command
 *  HystrixObservableCommand :是用来获取多条数据的Command
 *
 * -----------------------------------
 * @title: ItemCachePerwarmCommand
 * @projectName inventory
 * @date 2020/5/10 16:47
 */
public class ItemCachePerwarmCommand extends HystrixCommand<TbItemDTO> {

    private Long id;

    @Autowired
    private ItemFeign itemFeign;

    /**
     * description: 初始化构造方法 ， 需要传入你在run使用的值，并且传入全局变量
     * @param id item的ID
     * @Author: zeryts
     * @email: hezitao@agree.com
     * @Date: 2020/5/10 18:46
     */
    public ItemCachePerwarmCommand(Long id) {
        //绑定一个线程池
        super(HystrixCommandGroupKey.Factory.asKey("ItemCachePerwarmCommandGroup"));
        this.id = id;
    }


    @Override
    protected TbItemDTO run() throws Exception {
        return itemFeign.find(id).getData();
    }
}
