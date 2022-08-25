package com.bocft.meeting.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

/**
 * Swagger2配置信息
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                //只显示api路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build()
                .securitySchemes(Arrays.asList(securitySchemes()))
                .securityContexts(Arrays.asList(securityContexts()));
    }
    @Bean
    public Docket userApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("userApi")
                .apiInfo(webApiInfo())
                .select()
                //只显示user路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/user/.*")))
                .build()
                .securitySchemes(Arrays.asList(securitySchemes()))
                .securityContexts(Arrays.asList(securityContexts()));
    }
    @Bean
    public Docket adminApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build()
                .securitySchemes(Arrays.asList(securitySchemes()))
                .securityContexts(Arrays.asList(securityContexts()));

    }
    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("会议室预定系统-API文档")
                .description("本文档描述了预订会议接口定义")
                .version("1.0")
                .contact(new Contact("bocft", "xxx", "meeting_t@163.com"))
                .build();
    }

    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("会议预订后台管理系统-API文档")
                .description("本文档描述了预订会议接口定义")
                .version("1.0")
                .contact(new Contact("bocft", "xxx", "meeting_t@163.com"))
                .build();
    }

    /**
     * 配置全局参数，这里配置一个名为 Authorization 的请求头
     */
    public SecurityScheme securitySchemes(){
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * 配置有哪些请求需要携带 Token，这里我们配置了所有请求
     */
    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.none())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }
}
