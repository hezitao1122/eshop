package com.eshopinventory.inventory.config.filter;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

/**
 * @author zeryts
 * @description: 开启懒加载的过滤器
 * ```````````````````````````
 * @title: OpenEntityFilter
 * @projectName inventory
 * @date 2019/6/1 12:02
 */
//@WebFilter
//@Component
public class OpenEntityFilter extends OpenEntityManagerInViewFilter {
}
