package com.taro.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName BeanCopyUtil
 * Author taro
 * Date 2022/10/11 21:07
 * Version 1.0
 */

public class BeanCopyUtil {

    private BeanCopyUtil(){}

    //<T>说明是泛型方法，后面一个 T 说明返回类型是 T
    public static <T> T copyBean(Object source, Class<T> clazz){

        T ret = null;
        //创建目标对象
        try {
            ret = clazz.newInstance();
            BeanUtils.copyProperties(source, ret);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static <V, T> List<T> copyBeanList(List<V> list, Class<T> clazz){
        return list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }
}
