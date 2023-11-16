package com.alphay.boot.official.aspect;

import com.alibaba.fastjson2.JSON;import com.alphay.boot.official.annotation.RefreshCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RefreshCacheAspect {


    @Pointcut("@annotation(com.alphay.boot.official.annotation.RefreshCache)")
    public void refreshCachePointCut() {}


    /**
     * 方法执行后刷新redis缓存
     * @param joinPoint
     * @param keys
     * @return
     */
    @AfterReturning(value = "refreshCachePointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public Object afterMethod(JoinPoint joinPoint, Object keys) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获取注解拿到key
        RefreshCache refreshCache = method.getAnnotation(RefreshCache.class);
        String jsonString = JSON.toJSONString(joinPoint.getArgs());
        System.out.println("方法参数是"+jsonString);





        String s = refreshCache.keyType();


        System.out.println("方法执行后");
        return null;
    }
}
