package com.alphay.boot.official.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Redis数据缓存
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

    /**
     * @return 缓存的数据类型 如usertoken那在切面中的key  official:usertoken:25 如用户菜单数据usermenu 那在切面中的key  official:usermenu:25
     */
    String keyType() default "";
}
