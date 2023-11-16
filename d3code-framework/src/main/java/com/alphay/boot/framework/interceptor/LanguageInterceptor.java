package com.alphay.boot.framework.interceptor;


import com.alphay.boot.framework.ThreadLocals;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Baron
 * @date 2022/04/30
 */
@Component
@Slf4j
public class LanguageInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String acceptLanguage = StringUtils.trimToNull(request.getHeader("accept-language"));
        ThreadLocals.acceptLanguage.set(acceptLanguage);
        ThreadLocals.language.set(acceptLanguage);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
