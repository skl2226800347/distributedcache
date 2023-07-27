package com.skl.test.distributedcache.core.demo;

public class DemoManager extends AbstractDemo{

    @Override
    public void sayV2(String name) {
        System.out.println("[sayV2]子方法 name:"+name);
    }
}
