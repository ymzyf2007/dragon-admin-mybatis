package com.dragon.system.quartz.utils;

import com.dragon.common.utils.SpringContextHolder;
import com.dragon.common.utils.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ConcreteQuartzTaskRunnable implements Callable<Object> {

    private Object target;
    private Method method;
    private String params;

    public ConcreteQuartzTaskRunnable(String beanName, String methodName, String params) throws NoSuchMethodException {
        this.target = SpringContextHolder.getBean(beanName);
        this.params = params;
        if(StringUtils.isNotBlank(params)) {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    @Override
    public Object call() throws Exception {
        // 使method变为可访问
        ReflectionUtils.makeAccessible(method);
        if(StringUtils.isNotBlank(params)) {
            method.invoke(target, params);
        } else {
            method.invoke(target);
        }
        return null;
    }
}
