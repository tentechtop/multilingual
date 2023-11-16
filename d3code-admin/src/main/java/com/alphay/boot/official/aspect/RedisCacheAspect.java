package com.alphay.boot.official.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class RedisCacheAspect {










    private String key = "";

    /**
     * 设置缓存切入点 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.alphay.boot.official.annotation.RedisCache)")
    public void redisCachePointCut() {}


/*    @Before(value = "redisCachePointCut()")
    @SuppressWarnings("unchecked")
    public void beforeMethod(JoinPoint joinPoint) {

    }*/

/*
    @Around(value = "redisCachePointCut()")
    @SuppressWarnings("unchecked")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RedisCache redisCache = method.getAnnotation(RedisCache.class);
        if (redisCache.keyType().startsWith("official:user")){
            int uid = Math.toIntExact(SecurityUtils.getUserId());
            this.key =  redisCache.keyType()+uid;
        }else {
            //获取方法参数拿到Id

        }
        Object o = redisHelper.get(this.key);
        if (o==null){
            return pjp.proceed();
        }else {
            System.out.println("目标方法不再执行");
            return ApiResponseBuilder.success(o);
        }

    }
*/

/*    @AfterReturning(value = "redisCachePointCut()", returning = "keys")
    @SuppressWarnings("unchecked")
    public Object afterMethod(JoinPoint joinPoint, Object keys) {


        System.out.println("方法执行后");
        return null;
    }*/
}
