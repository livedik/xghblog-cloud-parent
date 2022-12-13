package com.blog.common.mybatis.model;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/***
 * 自动填充模型
 */
public class ModelMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        Object gmtCreate = this.getFieldValByName("gmtCreate", metaObject);
        if (null == gmtCreate)
        {
            this.setFieldValByName("gmtCreate",now,metaObject);
        }
        Object updateTime = this.getFieldValByName("updateTime", metaObject);
        if (null == updateTime) {
            this.setFieldValByName("gmtModify", now, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        Object updateTime = this.getFieldValByName("updateTime", metaObject);
        if (null == updateTime) {
            this.setFieldValByName("gmtModify", now, metaObject);
        }
    }
}
