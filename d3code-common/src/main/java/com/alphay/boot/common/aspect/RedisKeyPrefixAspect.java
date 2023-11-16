package com.alphay.boot.common.aspect;

import com.alphay.boot.common.core.redis.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RedisKeyPrefixAspect {
    @Autowired
    private RedisCache redisCache;

    @Pointcut("execution(* com.alphay.boot.common.core.redis.RedisCache.*(..))")
    public void redisCacheMethods() {
    }

    @Before("redisCacheMethods()")
    public void addPrefixToKey(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof String) {
            String originalKey = (String) args[0];
            String modifiedKey = "official:" + originalKey;
            args[0] = modifiedKey;
            System.out.println("args[0]"+args[0]);
        }
    }

/*    @AfterReturning(pointcut = "redisCacheMethods()", returning = "result")
    public void removePrefixFromKey(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        if (args.length > 0 && args[0] instanceof String) {
            String originalKey = (String) args[0];
            String modifiedKey = "official:" + originalKey;
            redisCache.renameKey(modifiedKey, originalKey);
        }
    }*/



}
