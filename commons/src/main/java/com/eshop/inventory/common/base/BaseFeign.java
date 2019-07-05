package com.eshop.inventory.common.base;

import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zeryts
 * @description: feign操作的公共类
 * ```````````````````````````
 * @title: BaseFeign
 * @projectName inventory
 * @date 2019/6/1823:17
 */
public interface BaseFeign<T extends BaseDTO, ID>{

    /**
     * 功能描述: 添加一条数据<br>
     * 〈〉
     * @param t 需要添加的数据
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto<T> add( @RequestBody T t);
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
    ResultDto<T> delete(@RequestParam ID id);
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
    ResultDto<T> find( @RequestParam ID id);
    /**
     * 功能描述: 修改一条数据<br>
     * 〈〉
     * @param t
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/15 17:33
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto<T> update(@RequestBody T t);
    /**
     * 功能描述: 根据实体类的条件进行查找<br>
     * 〈〉
     *
     * @param t
     * @return: com.eshop.inventory.common.dto.ResultDto<T>
     * @since: 1.0.0
     * @Author: zeryts
     * @Date: 2019/6/29 12:52
     */
    @RequestMapping(value = "/findByCondition",method =  RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResultDto<T> findByCondition( @RequestBody T t);

}
