package com.eshop.inventory.config.web;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * @author: zeryts
 * @date: 2018-09-04 22:33
 * @version: 1.0
 * @description: 用于restTemplate配置的类
 */
@Configuration
public class RestTemplateConfig {


    // 获取配置文件中自定义属性的超时时间
    @Value("${rest.ReadTimeout}")
    private int readTimeout;
    // 获取配置文件自定义属性中的连接时间
    @Value("${rest.ConnectTimeout}")
    private int connectionTimeout;

    /**
     * @功能描述:创建RestTemplate , 初始化Resttemplate
     */
    @Bean // 将此方法的bean给spring容器进行管理
    public RestTemplate restTemplate() {
        // 使用SimpleClient实现restTemplate
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        // 设置读取时间
        httpRequestFactory.setReadTimeout(readTimeout);
        // 设置超时时间
        httpRequestFactory.setConnectTimeout(connectionTimeout);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);

        // 获取RestTemplate默认配置好的所有转换器
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        // 默认的MappingJackson2HttpMessageConverter在第7个 先把它移除掉

        Iterator<HttpMessageConverter<?>> iterator = messageConverters.iterator();
        if (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            //原有的String是ISO-8859-1编码 去掉
            if (converter instanceof StringHttpMessageConverter) {
                iterator.remove();
            }
            //由于系统中默认有jackson 在转换json时自动会启用  但是我们不想使用它 可以直接移除或者将fastjson放在首位
            if (converter instanceof GsonHttpMessageConverter || converter instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
            }

        }
        // 设置编码格式
        messageConverters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        // 设置json转换工具 - 改为fastJson
        //配置FastJson
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        //将FastJson加入restTemplate配置中
        messageConverters.add(fastJsonHttpMessageConverter);

        return restTemplate;
    }


}
