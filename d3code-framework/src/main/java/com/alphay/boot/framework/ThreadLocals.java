package com.alphay.boot.framework;



/**
 * @author Baron
 * @date 2021-09-16
 */
public class ThreadLocals {
    /**
     * 当前的请求头 Accept-Language
     */
    public static final ThreadLocal<String> acceptLanguage = new ThreadLocal<>();

    /**
     * 当前支持的语言
     */
    public static final ThreadLocal<String> language = new ThreadLocal<>();
}
