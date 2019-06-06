package com.eshop.inventory.config.swagger;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
* @Description:    swagger-ui可视化接口配置类
* @Author:         WengGuoQi
* @CreateDate:     2018/7/2 15:30
* @Version:        1.0
*/

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //选择controller包
                .apis(RequestHandlerSelectors.basePackage("com.ph.football")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //自定义信息可按需求填写
                .title("足球宝贝API")
                .description("")
                .termsOfServiceUrl("")
                .contact("普华众鑫")
                .version("1.0")
                .build();
    }
  
}
