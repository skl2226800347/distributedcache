package com.skl.test.distributedcache.core.bytecode;

import com.skl.distributedcache.core.InvokerInvocationHandler;
import com.skl.test.distributedcache.core.demo.DemoService;
import org.junit.Test;

public class ProxyDemoTest {
    @Test
    public void getProxy(){
        ProxyDemo proxy = ProxyDemo.getProxy(DemoService.class);
        System.out.println(proxy);
        InvokerInvocationHandler handler = new InvokerInvocationHandler();
        DemoService demoService = (DemoService)proxy.newInstance(handler);
        System.out.println(demoService.get("xx"));
    }
}
