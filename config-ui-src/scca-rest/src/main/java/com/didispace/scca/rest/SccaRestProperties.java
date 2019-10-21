package com.didispace.scca.rest;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("scca.rest")
public class SccaRestProperties {

    /**
     * scca-rest¬的contextPath
     */
    private String contextPath = "";
    /**
     * 更改后是否刷新项目
     */
     private Integer flishFlag = 0;

}