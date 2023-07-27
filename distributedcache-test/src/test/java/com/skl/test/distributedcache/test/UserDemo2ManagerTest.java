package com.skl.test.distributedcache.test;

import com.skl.distributedcache.test.UserDemo2Manager;
import org.junit.Test;

import java.lang.reflect.Method;

public class UserDemo2ManagerTest {
    @Test
    public void t(){
        Method[] methods = UserDemo2Manager.class.getMethods();
        System.out.println(methods);
        for(Method method : methods) {
            System.out.println(method.getName());
        }
        System.out.println(methods.length);
    }
}
