package com.didispace.scca.rest.service;

import com.didispace.scca.rest.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author zeryts
 * @description: TODO
 * -----------------------------------
 * @title: FlushProjectService
 * @projectName amap
 * @date 2019/9/26 16:32
 */
@Service
@Slf4j
public class FlushProjectService {
    public static final String REFRESH_URI  = "/actuator/refresh";
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate template;

    public Result refresh(String appName){
        if(appName == null)
            return Result.ofFail(-1, "appName is not null!");
        List<ServiceInstance> instances = discoveryClient.getInstances(appName.toUpperCase());
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
