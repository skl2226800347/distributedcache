package com.skl.distributedcache.anno.api;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cached {
    String area() default CacheConstants.DEFAULT_AREA;

    String name() default CacheConstants.DEFAULT_NAME;

    CacheType cacheType() default CacheType.EXTERNAL;

    String key() default CacheConstants.UNDEFINED_STRING;

    String keyConvertor() default CacheConstants.UNDEFINED_STRING;

    String serialPolicy() default CacheConstants.UNDEFINED_STRING;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
    long expire() default CacheConstants.UNDEFINED_LONG;
}
