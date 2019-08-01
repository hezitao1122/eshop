package com.eshop.inventory.common.rebuild;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.ProductCacheApplication;
import com.eshop.inventory.common.queue.RebuildCacheQueue;
import com.eshop.inventory.config.zk.ZooKeeperSession;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.dto.TbItemDTO;
import com.eshop.inventory.manage.item.service.ItemCacheService;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

/**
 * @author zeryts
 * @description: 缓存重建的后台工作线程
 * ```````````````````````````
 * @title: RebuildCacheThread
 * @projectName inventory
 * @date 2019/7/2 23:13
 */
@Slf4j
public class RebuildCacheThread implements Runnable {
    //用于获取分布式锁的对象
    private ZooKeeperSession zooKeeperSession = ZooKeeperSession.getInstance();
    @Override
    public void run() {
        RebuildCacheQueue instance = RebuildCacheQueue.getInstance();
        while (true) {
            try {
                 Object obj = instance.take();
                if(obj instanceof TbItemDTO){
                    //代表是商品详情的信息
                    TbItemDTO dto = (TbItemDTO)obj;
                    zooKeeperSession.acquireDistributedLock(RedisCachePrefixEnum.ITEM.getName()+dto.getId());
                    try{
                        ItemCacheService itemCacheService = ProductCacheApplication.app.getBean(ItemCacheService.class);
                        //线从redis中获取数据
                        TbItemDTO loadCache = itemCacheService.getLoadCache(dto.getId());
                        if (loadCache != null) {
                            //比较当前数据的时间版本与已有数据的版本
                            Timestamp orderTime = loadCache.getUpdated();
                            Timestamp newTime = dto.getUpdated();
                            if (newTime.before(orderTime)) {//代表当前时间在老时间之前
                                log.info("此条新数据[{}]是在老数据[{}]之前!", newTime, orderTime);
                                continue;
                            }
                        }
                        log.info("Item ------> 将获取的商品信息id->[{}]", dto.getId(), ",内容->[{}]", JSON.toJSONString(dto), "保存至缓存和ehcache中!");
                        //缓存到ehcache中
                        itemCacheService.saveLoadEhCache(dto);
                        //缓存到redis中
                        itemCacheService.saveLoadCache(dto.getId(), dto);
                    }catch (Exception e){
                        log.info(e.toString(),e);
                    }finally {
                        //释放锁
                        log.info("释放锁，id=[{}]",dto.getId());
                        zooKeeperSession.releaseDistributedLock(RedisCachePrefixEnum.ITEM.getName()+dto.getId());
                    }
                }




            } catch (Exception e) {
                log.info(e.toString(), e);
            }
        }
    }
}
