package com.skl.test.distributedcache.core.bytecode;

import com.alibaba.fastjson.JSONObject;
import com.skl.distributedcache.core.bytecode.Proxy;
import com.skl.test.distributedcache.core.demo.DemoServiceImpl;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

public class ProxyTest {
    @Test
    public void invokeMethod()throws Exception{
        Method m = null;
        InvocationHandler invocationHandler = new InvocationHandler() {
            AtomicInteger incr = new AtomicInteger();
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("invoke...args:"+ JSONObject.toJSONString(args));
                int i = incr.incrementAndGet()%2;
                if (i == 0) {
                    return "直接拦截率";
                }
                return method.invoke(proxy,args);
            }
        };
        Class[] types={String.class};
        Object[]args = {"你好"};
        DemoServiceImpl demoService = new DemoServiceImpl();
        Proxy proxyV2 = Proxy.getProxy(demoService.getClass(),invocationHandler);
        System.out.println(proxyV2);
        Object object =  proxyV2.invokeMethod(demoService,"get",types,args);
        System.out.println("最终返回结果===========>"+object);
        object =  proxyV2.invokeMethod(demoService,"get",types,args);
        System.out.println("最终返回结果===========>"+object);
    }




}
