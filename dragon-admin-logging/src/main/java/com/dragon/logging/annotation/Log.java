package com.dragon.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    String value() default "";

    /**
     * 有些参数入request无法序列化，支持排除
     */
    String[] excludes() default { "request", "response" };
}
