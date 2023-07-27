package com.skl.test.distributedcache.core.bytecode;

import com.skl.test.distributedcache.core.demo.InvocationHandlerDemo;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerDemoTest {
    @Test
    public void t()throws Exception{
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("[InvocationHandler#invoke]进入调用处理程序中");
                return method.invoke(proxy,args);
            }
        };
        InvocationHandlerDemo invocationHandlerDemo = new InvocationHandlerDemo(invocationHandler);
        Method method =invocationHandlerDemo.getClass().getMethod("say");
        invocationHandlerDemo.invoke(method,null);
    }
}
