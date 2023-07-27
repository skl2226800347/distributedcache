package com.skl.test.distributedcache.test.object;

import com.skl.distributedcache.anno.api.Cached;
import com.skl.distributedcache.test.service.DemoService;
import com.skl.distributedcache.test.service.impl.DemoServiceImpl;
import jdk.internal.org.objectweb.asm.Type;
import org.junit.Test;

import java.lang.reflect.Method;

public class ClassTest {
    @Test
    public void getMethod() throws Exception{
        DemoServiceImpl demoService = new DemoServiceImpl();
        Method method = demoService.getClass().getMethod("get",String.class);
        System.out.println("getMethod="+method.getDeclaringClass().getName());

        System.out.println(Type.getMethodDescriptor(method));
    }

    @Test
    public void getV2() throws Exception{
        DemoServiceImpl demoService = new DemoServiceImpl();
        Method method = demoService.getClass().getMethod("getV2",String.class);
        System.out.println("getV2:"+method.getDeclaringClass().getName());
    }

    @Test
    public void get() throws Exception{
        Method method = DemoService.class.getMethod("get",String.class);
        System.out.println("get:"+method.getDeclaringClass().getName());
    }


    @Test
    public void getAnnotation() throws Exception{
        DemoServiceImpl demoService = new DemoServiceImpl();
        Method method = demoService.getClass().getMethod("get",String.class);
        System.out.println(method.getName());
        System.out.println(method.getDeclaringClass().getName());
        System.out.println(method.getAnnotation(Cached.class));
    }
}
