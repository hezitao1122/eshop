package com.eshop.inventory.manage.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.item.entity.TbItem;
import com.eshop.inventory.manage.item.mapper.ItemMapper;
import com.eshop.inventory.manage.item.service.ItemServiceI;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: TODO
 * ```````````````````````````
 * @title: ItemServiceImpl
 * @projectName inventory
 * @date 2019/5/30 23:37
 */
@Service
public class ItemServiceImpl extends BaseDBAndCacheServiceImpl<TbItem, Long> implements ItemServiceI {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ItemMapper itemMapper;


    @Override
    public TbItem update(TbItem tbItem) {
        log.info("===========日志===========: 已修改数据库中的库存，商品id=[{}]", JSON.toJSONString(tbItem));
        TbItem item = new TbItem();
        item.setId(tbItem.getId());
        item.setNum(tbItem.getNum());
        itemMapper.updateById(item);
        return item;
    }


    @Override
    public TbItem getNumById(Long itemId) {
        TbItem item = itemMapper.selectById(itemId);
        TbItem it = new TbItem();
        it.setId(itemId);
        it.setNum(item.getNum());
        return it;
    }

    @Override
    public void setCache(Long id, TbItem tbItem) {
        //       stringRedisTemplate.opsForValue().set(RedisCachePrefixEnum.ITEM_NUM.getName()+tbItem.getId(), JSON.toJSONString(tbItem));
        TbItem item = new TbItem();
        item.setNum(tbItem.getNum());
        item.setId(tbItem.getId());
        log.info("===========日志===========: 已更新商品库存的缓存，商品=[{}]", JSON.toJSONString(tbItem) + ", key = [{}]", RedisCachePrefixEnum.ITEM_NUM.getName() + tbItem.getId());
        redisTemplate.opsForValue().set(RedisCachePrefixEnum.ITEM_NUM.getName() + tbItem.getId(), item);
    }


    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.ITEM_NUM.getName();
    }

    @Override
    protected BaseMapper<TbItem> getMapper() {
        return itemMapper;
    }

}
