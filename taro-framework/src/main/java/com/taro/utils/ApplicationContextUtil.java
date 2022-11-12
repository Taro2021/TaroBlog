package com.taro.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ClassName ApplicationContextUtil
 * Author taro
 * Date 2022/11/11 14:56
 * Version 1.0
 */

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    @Autowired
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
}
