package com.eshopinventory.inventory.manage.content.repository;

import com.eshopinventory.inventory.manage.content.entity.TbContent;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: TbContent操作的JPA接口
 * ```````````````````````````
 * @title: ContentRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface ContentRepository extends JpaRepository<TbContent,Long> {
}
