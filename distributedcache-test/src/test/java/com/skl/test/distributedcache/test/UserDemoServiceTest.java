package com.skl.test.distributedcache.test;

import com.skl.distributedcache.test.UserDemoService;
import org.junit.Test;

import java.lang.reflect.Method;

public class UserDemoServiceTest {
    @Test
    public void t(){
        System.out.println(UserDemoService.class.isInterface());
        Method[] methods = UserDemoService.class.getMethods();
        System.out.println(methods);
        for(Method method : methods) {
            System.out.println(method.getName());
        }
        System.out.println(methods.length);
    }
}
