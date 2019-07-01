package com.eshop.inventory.manage.content.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.content.entity.TbContent;
import com.eshop.inventory.manage.content.mapper.ContentMapper;
import com.eshop.inventory.manage.content.service.ContentServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zeryts
 * @description: 内容操作的service层实现类
 * ```````````````````````````
 * @title: ContentServiceImpl
 * @projectName inventory
 * @date 2019/6/17 23:01
 */
@Service
public class ContentServiceImpl extends BaseDBAndCacheServiceImpl<TbContent, Long> implements ContentServiceI {
    @Autowired
    private ContentMapper contentMapper;



    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.CONTENT_CATEGORY.getName();
    }

    @Override
    protected BaseMapper<TbContent> getMapper() {
        return contentMapper;
    }
}
