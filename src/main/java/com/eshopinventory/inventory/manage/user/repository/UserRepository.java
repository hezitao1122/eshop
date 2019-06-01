package com.eshopinventory.inventory.manage.user.repository;

import com.eshopinventory.inventory.manage.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 * @description: User操作的操作类
 * ```````````````````````````
 * @title: UserRepository
 * @projectName inventory
 * @date 2019/5/2923:52
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
