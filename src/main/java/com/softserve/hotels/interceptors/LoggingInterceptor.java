package com.softserve.hotels.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class LoggingInterceptor {
    @Pointcut("execution(@com.softserve.hotels.annotations.Loggable * *(..))")
    public void methodAnnotatedWithLoggable() {
        /* pointcut */ }

    @Pointcut("within(@com.softserve.hotels.annotations.Loggable *)")
    public void beanAnnotatedWithLoggable() {
        /* pointcut */ }

    @Around("beanAnnotatedWithLoggable() || beanAnnotatedWithLoggable()")
    public void logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        final Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringType());
        StopWatch stopwatch = new StopWatch();
        stopwatch.start();
        joinPoint.proceed();
        stopwatch.stop();
        logger.debug(joinPoint.getSignature() + " called and worked for " + stopwatch.getTotalTimeMillis() + "ms.");
    }

    @AfterThrowing(value = "methodAnnotatedWithLoggable() || beanAnnotatedWithLoggable()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        final Logger logger = LogManager.getLogger(joinPoint.getSignature().getDeclaringType());
        logger.error(error);

    }
}
