package com.my.blog.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: MyBlog
 * @package: com.my.blog.config
 * @className: MybatisPlusConfig
 * @author: LYH
 * @description: TODO
 * @date: 2023/5/16 21:10
 * @version: 1.0
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new
                MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new
                PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
