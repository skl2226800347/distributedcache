package com.skl.distributedcache.test.service;

public interface DemoService {
    String get(String id);
    void remove(String id);

    void update(String id,String value);
}
