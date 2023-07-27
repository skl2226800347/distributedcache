package com.skl.test.distributedcache.core.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InvocationHandlerDemo {
    InvocationHandler invocationHandler ;
    public InvocationHandlerDemo(InvocationHandler invocationHandler ){
        this.invocationHandler = invocationHandler;
    }


    public void say(){
        System.out.println("你好吗");
    }
    public Object invoke(Method method,Object[] args){
        try {
            return invocationHandler.invoke(this, method, args);
        }catch (Throwable e){
            e.printStackTrace();
        }
        return null;
    }
}
