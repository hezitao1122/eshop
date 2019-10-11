package com.didispace.scca.rest.config;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class SccaErrorAttributes extends DefaultErrorAttributes {

    public SccaErrorAttributes() {
        super(false);
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(
                webRequest, includeStackTrace);
        errorAttributes.put("code", errorAttributes.get("status"));
        errorAttributes.remove("status");
        return errorAttributes;
    }

}