package com.eshopinventory.inventory.manage.item.service.impl;

import com.alibaba.fastjson.JSON;
import com.eshopinventory.inventory.common.enums.RedisCachePrefixEnum;
import com.eshopinventory.inventory.manage.item.entity.TbItem;
import com.eshopinventory.inventory.manage.item.mapper.ItemMapper;
import com.eshopinventory.inventory.manage.item.repository.ItemRepository;
import com.eshopinventory.inventory.manage.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 * @description: TODO
 * ```````````````````````````
 * @title: ItemServiceImpl
 * @projectName inventory
 * @date 2019/5/30 23:37
 */
@Service
@Slf4j
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ItemMapper itemMapper;


    @Override
    public void add(TbItem tbItem) {
        itemRepository.saveAndFlush(tbItem);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void update(TbItem tbItem) {
//        itemRepository.saveAndFlush(tbItem);
        log.info("===========日志===========: 已修改数据库中的库存，商品id=[{}]" , JSON.toJSONString(tbItem) );

        itemMapper.updateById(tbItem);
    }

    @Override
    public TbItem getById(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public void setCache(TbItem tbItem) {
        //       stringRedisTemplate.opsForValue().set(RedisCachePrefixEnum.ITEM.getName()+tbItem.getId(), JSON.toJSONString(tbItem));

        log.info("===========日志===========: 已更新商品库存的缓存，商品=[{}]" , JSON.toJSONString(tbItem) + ", key = [{}]", RedisCachePrefixEnum.ITEM.getName()+tbItem.getId());
        redisTemplate.opsForValue().set(RedisCachePrefixEnum.ITEM.getName()+tbItem.getId(),tbItem);
    }

    @Override
    public TbItem getCacheById(Long id) {
        String key = RedisCachePrefixEnum.ITEM.getName() + id;
        TbItem tbItem = (TbItem)redisTemplate.opsForValue().get(key);
        return tbItem;
//        String s = stringRedisTemplate.opsForValue().get(key);
//        return JSON.parseObject(s,TbItem.class);
    }

    @Override
    public void deleteCacheById(Long id) {
        log.info("===========日志===========: 已删除redis中的缓存，key=[{}]" , RedisCachePrefixEnum.ITEM.getName()+id);
        redisTemplate.delete(RedisCachePrefixEnum.ITEM.getName()+id);
//         stringRedisTemplate.delete(RedisCachePrefixEnum.ITEM.getName()+id);
    }
}
