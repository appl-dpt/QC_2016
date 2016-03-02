package com.softserve.hotels.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExceptionAdviceInterceptor {

    @Pointcut("within(com.softserve.hotels.handlers.ExceptionAdvice)")
    public void inExceptionAdvice() {
        /* @AspectJ pointcut */ }

    @Before("inExceptionAdvice()")
    public void logErrors(JoinPoint joinPoint) {
        final Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringType());
        Exception e = (Exception) joinPoint.getArgs()[0];
        // TODO: remove after debug
        e.printStackTrace();
        logger.warn(e);
    }
}
