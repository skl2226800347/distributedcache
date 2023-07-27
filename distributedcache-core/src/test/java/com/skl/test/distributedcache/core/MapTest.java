package com.skl.test.distributedcache.core;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapTest {
    @Test
    public void computeIfAbsent(){
        Map<String,String> map = new ConcurrentHashMap<String,String>();
        String value = map.computeIfAbsent("abc",x->{
            System.out.println("x:"+x);
            return "123";
        });
        System.out.println("value:"+value);
        value = map.computeIfAbsent("abc",x->{
            System.out.println("x:"+x);
            return "123";
        });
        System.out.println("value:"+value);
    }

    @Test
    public void putIfAbsent(){
        Map<String,String> map = new ConcurrentHashMap<String,String>();
        String value = map.putIfAbsent("bcd","123");
        System.out.println("value:"+value);
        value = map.putIfAbsent("bcd","123");
        System.out.println("value:"+value);
    }
}
