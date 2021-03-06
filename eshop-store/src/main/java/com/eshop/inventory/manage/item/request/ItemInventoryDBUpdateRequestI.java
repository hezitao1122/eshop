package com.eshop.inventory.manage.item.request;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.common.base.IBaseWriterRequest;
import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.service.ItemServiceI;
import org.slf4j.Logger;

import java.util.Map;

/**
 * @author zeryts
 * @description: 比如说一个商品发生了交易，那么就要修改这个商品对应的库存
 * <p>
 * 此时就会发送请求过来，要求修改库存，那么这个可能就是所谓的data update request，数据更新请求
 * <p>
 * cache aside pattern
 * <p>
 * （1）删除缓存
 * （2）更新数据库
 * <p>
 * 项目，我们尽可能在电商的业务背景下，用整个电商的业务串起来，讲解，在项目里去学习知识，而不是干讲各种解决方案
 * <p>
 * 真实的场景，有大量的业务在里面，涉及几十个字段，可能过来的是一个什么什么请求
 * <p>
 * 然后你得计算之后，才知道它最终的库存是多少
 * <p>
 * 电商系统，少则几十个人，多则几百个人，做少则半年一年，多则好多年，大量复杂的业务逻辑代码
 * <p>
 * 课程，几十个小时，撑死一百个小时，相当于一个工程师连续工作半个多月
 * <p>
 * 我讲课，尽量浓缩了精华，我写代码比一般工程师快一些，相当于一个工程连续工作1个月
 * <p>
 * 这也出不来太多的东西啊。。。
 * <p>
 * 为了讲课，我们要明白，我讲课是为了教你什么东西？为了教你架构的能力，支撑高并发的缓存架构
 * <p>
 * 我不是在教你怎么去做一个电商网站的库存系统，商品详情页系统
 * <p>
 * 我是说拿我参与过的真实的项目作为背景，浓缩和简化了业务以后，在这个业务背景下，去教你架构的知识
 * <p>
 * 可以理论结合实际，在业务中去学习，这样的话，效果肯定会好很多
 * ```````````````````````````
 * @title: ItemInventoryDBUpdateRequestI
 * @projectName inventory
 * @date 2019/5/31 21:45
 */
public class ItemInventoryDBUpdateRequestI implements Request<TbItem, Long>, IBaseWriterRequest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ItemInventoryDBUpdateRequestI.class);
    /**
     * 商品信息
     */
    private TbItem item;

    /**
     * 商品操作的service
     */
    private ItemServiceI itemService;

    public ItemInventoryDBUpdateRequestI(TbItem item, ItemServiceI itemService) {
        this.item = item;
        this.itemService = itemService;
    }

    @Override
    public void process() {
        log.info("===========日志===========: 数据库更新请求开始执行，商品id=[{}]", item.getId() + ", 商品=[{}]", JSON.toJSONString(item));
        // 删除redis中的缓存
        itemService.deleteCacheById(item.getId());

        try {
            System.out.println("睡眠一下");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info(e.toString(), e);
        }


        //修改数据库信息
        itemService.update(item);
    }

    @Override
    public Long getId() {
        return item.getId();
    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }

    @Override
    public Map<Long, Boolean> getCacheMap() {
        return null;
    }
}
