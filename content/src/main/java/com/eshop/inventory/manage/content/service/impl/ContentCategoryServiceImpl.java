package com.eshop.inventory.manage.content.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.eshop.inventory.common.base.impl.BaseDBAndCacheServiceImpl;
import com.eshop.inventory.manage.common.enums.RedisCachePrefixEnum;
import com.eshop.inventory.manage.content.entity.TbContentCategory;
import com.eshop.inventory.manage.content.mapper.ContentCategoryMapper;
import com.eshop.inventory.manage.content.service.ContentCategoryServiceI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author zeryts
 * @description: 内容操作的service层实现类
 * ```````````````````````````
 * @title: ContentCategoryServiceImpl
 * @projectName inventory
 * @date 2019/6/17 23:11
 */
public class ContentCategoryServiceImpl extends BaseDBAndCacheServiceImpl<TbContentCategory, Long> implements ContentCategoryServiceI {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;


    @Override
    protected String getPrefix() {
        return RedisCachePrefixEnum.CONTENT.getName();
    }

    @Override
    protected BaseMapper<TbContentCategory> getMapper() {
        return contentCategoryMapper;
    }
}
