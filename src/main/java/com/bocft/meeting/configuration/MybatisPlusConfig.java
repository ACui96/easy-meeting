package com.bocft.meeting.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 分页拦截器
 */
@Configuration
@EnableTransactionManagement//事务
public class MybatisPlusConfig {
    @Bean
    //自定义分页拦截器
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();

    }



}
