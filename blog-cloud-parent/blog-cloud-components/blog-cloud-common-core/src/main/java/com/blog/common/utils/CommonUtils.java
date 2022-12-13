package com.blog.common.utils;

import java.util.UUID;

/***
 * 常用工具类
 */
public class CommonUtils {

//    生产去掉-的 UUID 编码
    public static String getUUID()
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}
