/*
package com.bocft.meeting.configuration;


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

*/
/*
 * 该类是一个配置类，它要配置SpringBoot集成shiro框架*//*

@Configuration
public class ShiroConfig {

    //连接数据
    @Bean
    public MyRealm getMyRealm(){
        return new MyRealm();
    }

    //管理所有用户
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(){
        DefaultWebSecurityManager dws = new DefaultWebSecurityManager();
        dws.setRealm(this.getMyRealm());
        return dws;
    }

    //管理用户
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        filter.setSecurityManager(getDefaultWebSecurityManager());
        //filter.setLoginUrl("index.html");
        //filter.setUnauthorizedUrl("index.html");
        Map<String,String> map = new LinkedHashMap<>();
        //不需要登录访问的资源
        //map.put("/api/userLogin/**","anon");
        //注销用户过滤器
        //map.put("","logout");
        //需要登录访问的资源
        //map.put("/dept/deptIndex","authc");
        //map.put("user/update","authc");
        filter.setFilterChainDefinitionMap(map);
        return filter;
    }

}
*/
