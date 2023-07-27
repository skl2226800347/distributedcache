package com.skl.distributedcache.anno.api;

public interface CacheConstants {
    String DEFAULT_AREA="default";
    String DEFAULT_NAME="default";
    String UNDEFINED_STRING="$$undefined$$";

    long UNDEFINED_LONG = Long.MIN_VALUE;
    long UNDEFINED_INT = Integer.MIN_VALUE;

    String DEFAULT_SERIAL_POLICY = SerialPolicy.JAVA;


     static boolean isUndefined(String str){
        return UNDEFINED_STRING.equals(str);
    }
}
