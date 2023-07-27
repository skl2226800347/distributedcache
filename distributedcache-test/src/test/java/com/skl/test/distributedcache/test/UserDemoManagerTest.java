package com.skl.test.distributedcache.test;

import com.skl.distributedcache.test.UserDemoManager;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class UserDemoManagerTest {
    @Test
    public void getMethod()throws Exception{
        UserDemoManager userDemoManager = new UserDemoManager();
        Method  method = userDemoManager.getClass().getMethod("display");
        System.out.println(method.getParameterTypes());
        System.out.println(method.getParameterTypes().length);

        System.out.println(Modifier.isPublic(method.getModifiers()));
    }

    @Test
    public void getMethodv2()throws Exception{
        UserDemoManager userDemoManager = new UserDemoManager();
        Method[]  declaredMethods = userDemoManager.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println("declaredMethod="+method.getName());
        }
        Method[] methods = userDemoManager.getClass().getMethods();
        for (Method method : methods) {
            System.out.println("method="+method.getName());
        }
    }
}
