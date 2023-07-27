package com.skl.test.distributedcache.core.demo;

import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.List;

public class DemoServiceImpl implements DemoService{
    @Override
    public Object get(String key) {
        return "返回值:"+key;
    }

    @Override
    public Object get(String area, String key) {
        System.out.println("[DemoServiceImpl#get] area:"+area+"  key:"+key);
        return "返回值:"+area+"  key:"+key;
    }

    @Override
    public void print(String value) {
        System.out.println("print:"+value);
    }

    public String getV2(String area){
        return "你好";
    }

    public List<String> getList(List<String> param1,String name){
        System.out.println("[getList]param1:"+ JSONObject.toJSONString(param1));
        return Arrays.asList("name");
    }
}
