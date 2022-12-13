package com.blog.autoconfigure;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.blog.common.annotation.RequestMappingScan;
import com.blog.common.mybatis.model.ModelMetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/***
 * 默认配置类
 */
@Slf4j
@Configuration
public class AutoConfiguration {

    @Autowired
    private AmqpTemplate amqpTemplate;


    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }


    @Bean
    public MetaObjectHandler modelMetaObjectHandler()
    {
        ModelMetaObjectHandler metaObjectHandler = new ModelMetaObjectHandler();
        return metaObjectHandler;

    }

    @Bean
    public RequestMappingScan resourceScan()
    {
        RequestMappingScan requestMappingScan = new RequestMappingScan(amqpTemplate);
        return requestMappingScan;
    }

    /**
     * 分页插件配置
     *
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 向MyBatis-Plus的过滤器链中添加分页拦截器，需要设置数据库类型（主要用于分页方言）
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}
