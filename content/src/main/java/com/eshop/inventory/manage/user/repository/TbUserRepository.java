package com.eshop.inventory.manage.user.repository;

import com.eshop.inventory.manage.user.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: 用户表操作的JPA接口
 * ```````````````````````````
 * @title: TbOrderRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface TbUserRepository extends JpaRepository<TbUser,Long> {
}
