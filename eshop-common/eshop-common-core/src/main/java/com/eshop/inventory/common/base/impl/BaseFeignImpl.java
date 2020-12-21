package com.eshop.inventory.common.base.impl;

import com.alibaba.fastjson.JSON;
import com.eshop.inventory.common.dto.BaseDTO;
import com.eshop.inventory.common.dto.ResultDto;
import com.eshop.inventory.common.dto.ResultEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zeryts
 * @description: Feign回调的通用类
 * ```````````````````````````
 * @title: BaseFeignImpl
 * @projectName inventory
 * @date 2019/6/29 12:53
 */
@Slf4j
public abstract class BaseFeignImpl<T extends BaseDTO, ID> {

    //    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResultDto<T> add(T t) {
        log.info("增加[{}]时产生异常!数据为:[{}]", getClassName(), JSON.toJSONString(t));
        return new ResultDto(ResultEnum.FEIGN_ERROR, "增加" + getClassName() + "时产生异常!");
    }

    //    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ResultDto<T> delete(ID id) {
        log.info("删除[{}]时产生异常!数据为:[{}]", getClassName(), id);
        return new ResultDto(ResultEnum.FEIGN_ERROR, "删除" + getClassName() + "时产生异常!");
    }

    //    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public ResultDto<T> find(ID id) {
        log.info("查询[{}]时产生异常!数据为:[{}]", getClassName(), id);
        return new ResultDto(ResultEnum.FEIGN_ERROR, "查询" + getClassName() + "时产生异常!");
    }

    //    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResultDto<T> update(T t) {
        log.info("修改[{}]时产生异常!数据为:[{}]", getClassName(), JSON.toJSONString(t));
        return new ResultDto(ResultEnum.FEIGN_ERROR, "修改" + getClassName() + "时产生异常!");
    }

    //    @RequestMapping(value = "/findByCondition",method =  RequestMethod.POST)
    public ResultDto<T> findByCondition(T t) {
        log.info("查询[{}]时产生异常!数据为:[{}]", getClassName(), t);
        return new ResultDto(ResultEnum.FEIGN_ERROR, "查询" + getClassName() + "时产生异常!");
    }

    public ResultDto<T> findByIds(List<ID> ids) {
        log.info("批量查询[{}]时产生异常!数据为:[{}]", getClassName(), ids);
        return new ResultDto(ResultEnum.FEIGN_ERROR, "查询" + getClassName() + "时产生异常!");
    }


    public abstract String getClassName();
}
