package com.skl.test.distributedcache.core.demo;

public interface DemoService {
    Object get(String key);
    Object get(String area,String key);
    void print(String value);
}
