package com.skl.test.distributedcache.test.reflect;

import com.skl.distributedcache.test.UserDemoManager;
import org.junit.Test;

import java.lang.reflect.Method;

public class ReflectTest {
    @Test
    public void staticMethod()throws Exception{
        UserDemoManager userDemoManager = new UserDemoManager();
        Class cls = userDemoManager.getClass();
        Method method = cls.getMethod("acuire",String.class);
        Object value =  method.invoke(null,"你好吗");
        System.out.println("value      "+value);
    }

    @Test
    public void nonStaticMethod()throws Exception{
        UserDemoManager userDemoManager = new UserDemoManager();
        Class cls = userDemoManager.getClass();
        Method method = cls.getMethod("get",String.class);
        Object value =  method.invoke(null,"你好吗");
        System.out.println("value      "+value);
    }
}
