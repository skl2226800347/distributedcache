package com.skl.distributedcache.core.utils;

public class StringUtils {
    public static final boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
    public static final boolean isEmpty(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }
    public static final String convertString(String ...strs){
        StringBuilder builder = new StringBuilder();
        for(String str:strs){
            builder.append(str);
        }
        return builder.toString();
    }
}
