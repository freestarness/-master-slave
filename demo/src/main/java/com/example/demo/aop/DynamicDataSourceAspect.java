package com.example.demo.aop;

import com.example.demo.annotation.TargetDataSource;
import com.example.demo.config.DynamicDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {

    @Around("execution(public * com.example.demo.service..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method targetMethod = methodSignature.getMethod();
        Annotation[] annotations =  targetMethod.getDeclaredAnnotations();
        if(targetMethod.isAnnotationPresent(TargetDataSource.class)){
            String targetDataSource = targetMethod.getAnnotation(TargetDataSource.class).value() ;
            DynamicDataSource.setDataSource(targetDataSource);
        }
        try {
            return pjp.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }
}
