package com.bocft.meeting.configuration;

import com.bocft.meeting.common.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置类, 验证请求头中是否带有token，登录接口放行*/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

/*    *
     * 注册拦截器
     * addInterceptor: 注册拦截器对象
     * addPathPatterns: 拦截的请求
     * excludePathPatterns: 拦截白名单
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v2/**",
                        "/swagger-ui.html/**",
                        "/api/userLogin/**",
                        "/api/userLogin/sendSms/**",
                        "/admin/repair/export",
                        //以下仅供测试开放，记得关闭
                        "/**"
                        );
    }

}
