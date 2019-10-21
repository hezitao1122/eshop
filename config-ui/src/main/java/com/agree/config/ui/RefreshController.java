package com.agree.config.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zeryts
 * @description: 刷新项目的控制台
 * -----------------------------------
 * @title: RefreshController
 * @projectName amap
 * @date 2019/9/17 20:04
 */
@RestController
@RequestMapping("/refresh")
@Slf4j
public class RefreshController {
    public static final String REFRESH_URI  = "/actuator/refresh";
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate template;

    @PostMapping
    public Result refresh(String appName){
        if(appName == null)
            return Result.ofFail(-1, "appName is not null!");
        List<ServiceInstance> instances = discoveryClient.getInstances(appName.toUpperCase());
        if(1 ==  1) return Result.ofSuccess(null);
        instances.forEach(in ->{
            URI uri = in.getUri();
            try{
                template.getForObject(uri+REFRESH_URI,String.class);
            }catch (Exception e){
                log.info(e.toString(),e);
                Result.ofSuccessMsg("uri["+uri.toString()+"] refresh Config error ! "+ e.getMessage());
            }
        });
        return Result.ofSuccess(null);
    }

}
