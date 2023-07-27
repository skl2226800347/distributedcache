package com.skl.distributedcache.core.support;

import com.alibaba.fastjson.JSONObject;

import java.util.function.Function;

public class FastjsonKeyConvertor implements Function<Object,Object> {
    public static final FastjsonKeyConvertor INSTANCE = new FastjsonKeyConvertor();
    @Override
    public Object apply(Object key) {
        if (key == null){
            return null;
        }
        if(key instanceof String){
            return key;
        }
        return JSONObject.toJSONString(key);
    }
}
