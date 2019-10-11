package com.didispace.scca.rest.web;

import com.alibaba.fastjson.JSON;
import com.didispace.scca.core.domain.Env;
import com.didispace.scca.core.domain.EnvParam;
import com.didispace.scca.rest.dto.EnvParamDto;
import com.didispace.scca.rest.dto.base.WebResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Created by 程序猿DD/翟永超 on 2018/4/27.
 * <p>
 * Blog: http://blog.didispace.com/
 * Github: https://github.com/dyc87112/
 */
@Api("EnvParam MGT（环境参数管理）")
@Slf4j
@RestController
@RequestMapping("${scca.rest.context-path:}/envParam")
public class EnvParamController extends BaseController {

    @ApiOperation("List EnvParam / 环境参数列表")
    @RequestMapping(method = RequestMethod.GET)
    public WebResp<List<EnvParamDto>> findEnvParamsByEnv(@RequestParam("envId") String envId) {
        List<EnvParamDto> result = new ArrayList<>();

        for (EnvParam envParam : envParamRepo.findAllByEnv_Id(envId)) {
            EnvParamDto dto = new EnvParamDto();
            log.info("envParam={}", JSON.toJSONString(envParam));
            BeanUtils.copyProperties(envParam, dto);
            result.add(dto);
        }

        return WebResp.success(result);
    }

    @ApiOperation("Add EnvParam / 添加一个环境参数")
    @RequestMapping(method = RequestMethod.POST)
    public WebResp<String> addEnvParam(@RequestParam("envId") String envId, @RequestBody EnvParamDto envParam) {
        log.info("add an EnvParam to {} : {} ", envId, JSON.toJSONString(envParam));

        EnvParam saveEnvParam = new EnvParam();
        BeanUtils.copyProperties(envParam, saveEnvParam);
        Optional<Env> optional = envRepo.findById(envId);
        saveEnvParam.setEnv(optional.get());
        envParamRepo.save(saveEnvParam);

        return WebResp.success("save EnvParam success");
    }

    @ApiOperation("Update EnvParam / 更新某个环境参数")
    @RequestMapping(method = RequestMethod.PUT)
    public WebResp<String> updateEnvParam(@RequestBody EnvParamDto envParam) {
        log.info("update envParam. envParam={}", JSON.toJSONString(envParam));
        Optional<EnvParam> optional = envParamRepo.findById(envParam.getId());
        EnvParam updateEnvParam = optional.get();

        log.info("updateEnvParam={}", JSON.toJSONString(updateEnvParam));

        updateEnvParam.setPKey(envParam.getPKey());
        updateEnvParam.setPValue(envParam.getPValue());
        envParamRepo.save(updateEnvParam);

        return WebResp.success("update EnvParam success");
    }

    @ApiOperation("Delete EnvParam / 删除某个环境参数")
    @RequestMapping(method = RequestMethod.DELETE)
    public WebResp<String> delete(@RequestParam("id") String id) {
        EnvParam envParam = new EnvParam();
        envParam.setId(id);
        Example<EnvParam> example = Example.of(envParam);
        Optional<EnvParam> op = envParamRepo.findOne(example);
        envParam = op.get();

        log.info("delete envParam. envParam={}", JSON.toJSONString(envParam));

        envParamRepo.delete(envParam);

        return WebResp.success("delete EnvParam success");
    }

    /**
     * batch opt / 批量操作
     **/

    @Transactional
    @ApiOperation("Add EnvParams / 新增一批环境参数")
    @RequestMapping(path = "/batch", method = RequestMethod.POST)
    public WebResp<String> createBatch(@RequestParam("envId") String envId, @RequestBody List<EnvParamDto> envParams) {
        Optional<Env> optional = envRepo.findById(envId);
        Env env = optional.get();
        log.info("add EnvParams to {} : {} ", env.getName(), envParams);

        for (EnvParamDto envParam : envParams) {
            EnvParam saveEnvParam = new EnvParam();
            BeanUtils.copyProperties(envParam, saveEnvParam);
            saveEnvParam.setEnv(env);
            envParamRepo.save(saveEnvParam);
        }

        return WebResp.success("batch add EnvParams ：" + envParams.size());
    }

    @Transactional
    @ApiOperation("Delete EnvParams / 删除一批环境参数")
    @RequestMapping(path = "/batch", method = RequestMethod.DELETE)
    public WebResp<String> deleteBatch(@RequestBody List<String> envParamIds) {
        log.info("delete EnvParams : {} ", envParamIds);

        for (String id : envParamIds) {
            EnvParam param = new EnvParam();
            param.setId(id);
            envParamRepo.delete(param);
        }

        return WebResp.success("batch delete EnvParams ：" + envParamIds.size());
    }

}
