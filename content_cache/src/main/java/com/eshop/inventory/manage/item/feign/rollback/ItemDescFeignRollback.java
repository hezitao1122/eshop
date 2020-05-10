package com.eshop.inventory.manage.item.feign.rollback;

import com.eshop.inventory.common.base.impl.BaseFeignImpl;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.manage.item.dto.TbItemDescDTO;
import com.eshop.inventory.manage.item.feign.ItemDescFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zeryts
 * @description: feign操作失败的类
 * ```````````````````````````
 * @title: ItemFeignRollback
 * @projectName inventory
 * @date 2019/6/18 23:20
 */
@Component
@Slf4j
public class ItemDescFeignRollback extends BaseFeignImpl<TbItemDescDTO, Long> implements ItemDescFeign {

    @Override
    public String getClassName() {
        return getClassName();
    }


    @Override
    public ResultDto<TbItemDescDTO> findByItemIds(List<Long> itemIds) {
        return null;
    }
}
