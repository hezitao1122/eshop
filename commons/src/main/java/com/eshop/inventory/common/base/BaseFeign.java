package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.entity.BaseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author zeryts
 * @description: feign操作的公共类
 * ```````````````````````````
 * @title: BaseFeign
 * @projectName inventory
 * @date 2019/6/1823:17
 */
public interface BaseFeign<T extends BaseEntity, ID>{

    /**
     * 功能描述: 添加一条数据<br>
     * 〈〉
     * @param t 需要添加的数据
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
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
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
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
    @RequestMapping(value = "/find",method = RequestMethod.GET)
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
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    ResultDto<T> update(T t);

}
