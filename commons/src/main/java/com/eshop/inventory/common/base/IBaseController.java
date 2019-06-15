package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.entity.BaseEntity;

/**
 * @author zeryts
 * @description: controller抽象
 * ```````````````````````````
 * @title: IBaseController
 * @projectName inventory
 * @date 2019/6/1517:31
 */
public interface IBaseController<T extends BaseEntity, ID> {
    /**
     * 功能描述: 添加一条数据<br>
     * 〈〉
     * @param t 需要添加的数据
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    ResultDto<T> add(T t);
    /**
     * 功能描述: 删除一条数据<br>
     * 〈〉
     * @param id 需要数据的id
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    ResultDto<T> delete(ID id);
    /**
     * 功能描述: 根据id查找一条数据<br>
     * 〈〉
     * @param id
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    ResultDto<T> find(ID id);
    /**
     * 功能描述: 修改一条数据<br>
     * 〈〉
     * @param t
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    ResultDto<T> update(T t);


}
