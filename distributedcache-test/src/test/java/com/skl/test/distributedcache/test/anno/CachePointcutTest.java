package com.skl.test.distributedcache.test.anno;

import com.skl.distributedcache.anno.aop.CachePointcut;
import com.skl.distributedcache.test.UserDemoManager;
import org.junit.Test;

import java.lang.reflect.Method;

public class CachePointcutTest {
    @Test
    public void getKey()throws Exception{
        UserDemoManager userDemoService = new UserDemoManager();
        Method method = userDemoService.getClass().getMethod("get",String.class);
        String key = CachePointcut.getKey(method,userDemoService.getClass());
        System.out.println(key);
        System.out.println("jetcache.samples.springboot.UserServiceImpl.loadUser(J)Ljetcache/samples/springboot/User;_jetcache.samples.springboot.UserServiceImpl");
    }
}
