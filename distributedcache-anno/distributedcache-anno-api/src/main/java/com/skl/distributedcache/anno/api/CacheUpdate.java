package com.skl.distributedcache.anno.api;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheUpdate{
    String area() default CacheConstants.DEFAULT_AREA;

    String name() default CacheConstants.DEFAULT_NAME;

    String key() default CacheConstants.UNDEFINED_STRING;


    String value() ;
}
