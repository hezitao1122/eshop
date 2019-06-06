package com.eshopinventory.inventory.manage.content.repository;

import com.eshopinventory.inventory.manage.content.entity.TbContentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Administrator
 * @description: TbContentCategory操作的JPA接口
 * ```````````````````````````
 * @title: ContentCategoryRepository
 * @projectName inventory
 * @date 2019/5/3021:50
 */
public interface ContentCategoryRepository extends JpaRepository<TbContentCategory,Long> {
}
