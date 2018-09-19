package com.imooc.sell.constant;

/**
 * redis常量
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%s";

    Integer EXPIRE = 7200; //过期时间2小时
}
