package com.skl.test.distributedcache.test.collection;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class HashMapTest {
    @Test
    public void keySet_stream_V2(){
        Map<String,Object>  hashMap = new HashMap<>();
        hashMap.put("default.type","linkedhashmap");
        Set<String> set = hashMap.keySet().stream().map((s)->s.substring(0,s.indexOf("."))).collect(Collectors.toSet());
        System.out.println(set);
    }
    @Test
    public void keySet_stream(){
        Map<String,Object>  hashMap = new HashMap<>();
        hashMap.put("type",2);

        Set<String> set = hashMap.keySet().stream().map(s->{
            System.out.println(s);
            return s;
        }).collect(Collectors.toSet());
        System.out.println(set);

    }

    @Test
    public void match(){
        Map<String,Object>  hashMap = new HashMap<>();
        hashMap.put("default.type","linkedhashmap");
        Set<String> set = hashMap.keySet().stream().map((s)->s.substring(0,s.indexOf("."))).collect(Collectors.toSet());
        String[] cacheTypes={"linkedhashmap"};
        List<String> cacheTypeList = Arrays.asList(cacheTypes);

        boolean isMatch = set.stream().anyMatch(s-> {
            return cacheTypeList.contains(hashMap.get(s+".type"));
        });
        System.out.println(isMatch);
        isMatch = set.stream().anyMatch((s)-> cacheTypeList.contains(s));
        System.out.println(isMatch);
    }

    @Test
    public void filter(){
        Map<String,Object>  hashMap = new HashMap<>();
        hashMap.put("default.type","linkedhashmap");
        hashMap.put("A","linkedhashmap");
        Set<String> set =hashMap.keySet().stream().map(s-> s.indexOf('.') >0?s.substring(0,s.indexOf('.')):null).filter(s->s != null).collect(Collectors.toSet());
        System.out.println(JSONObject.toJSONString(set));
    }

    @Test
    public void filter2(){
        Map<String,Object>  hashMap = new HashMap<>();
        hashMap.put("default.type","linkedhashmap");
        hashMap.put("A","linkedhashmap");
        System.out.println(hashMap.keySet().stream().map((s)->s.indexOf('.')>0?s.substring(0,s.indexOf('.')):null).filter(s->s != null).collect(Collectors.toSet()));
    }

    @Test
    public void putIfAbsent(){
        Map<String,String> map = new HashMap<>();
        String value1 = map.putIfAbsent("xx","张三");
        System.out.println(map+"    value1:"+value1);
        value1 = map.putIfAbsent("xx","王五");
        System.out.println(map+"    value1:"+value1);
    }

    @Test
    public void computeIfAbsent(){
        Map<String,String> map = new HashMap<>();
        String value1 = map.computeIfAbsent("xx",s->{return "张三";});
        System.out.println(map+"    value1:"+value1);
        value1 = map.computeIfAbsent("xx",x->{return "王五";});
        System.out.println(map+"    value1:"+value1);
    }




}
