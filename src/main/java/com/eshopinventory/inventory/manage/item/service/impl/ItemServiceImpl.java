package com.eshopinventory.inventory.manage.item.service.impl;

import com.eshopinventory.inventory.common.enums.RedisCachePrefixEnum;
import com.eshopinventory.inventory.manage.item.entity.TbItem;
import com.eshopinventory.inventory.manage.item.repository.ItemRepository;
import com.eshopinventory.inventory.manage.item.service.ItemService;
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
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public void add(TbItem tbItem) {
        itemRepository.save(tbItem);
    }

    @Override
    public void delete(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public void update(TbItem tbItem) {
        itemRepository.save(tbItem);
    }

    @Override
    public TbItem getById(Long id) {
        return itemRepository.getOne(id);
    }

    @Override
    public void setCache(TbItem tbItem) {
        //       stringRedisTemplate.opsForValue().set(RedisCachePrefixEnum.ITEM.getName()+tbItem.getId(), JSON.toJSONString(tbItem));
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

       redisTemplate.delete(RedisCachePrefixEnum.ITEM.getName()+id);
//         stringRedisTemplate.delete(RedisCachePrefixEnum.ITEM.getName()+id);
    }
}
