package com.taro.aspect;

import com.alibaba.fastjson.JSON;
import com.taro.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * ClassName LogAspect
 * Author taro
 * Date 2022/10/21 18:48
 * Version 1.0
 */
//切面类
@Component
@Aspect//切面类注解
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.taro.annotation.SystemLog)")//AOP 切点
    public void pt(){};

    @Around("pt()") //环绕通知,指定环绕的切点
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {//方法的增强需要将异常抛出，让全局异常去处理

        Object ret = null;
        try {
            handleBefore(joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
            log.info("==============End==============" + System.lineSeparator());
        }

        return ret;
    }

    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response     : {}", JSON.toJSONString(ret));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {

        //获取 request
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //获取被增强的方法上的注解对象
        SystemLog systemLog = getSystemLog(joinPoint);


        log.info("=============Start=============");
        // 打印请求 URL
        log.info("URL          : {}",request.getRequestURL());
        // 打印描述信息，在注解 SystemLog 中设置的信息
        log.info("BusinessName : {}", systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method  : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP           : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    //获取被增强方法上的注解对象
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        SystemLog annotation = method.getAnnotation(SystemLog.class);;
        return  annotation;
    }


}
