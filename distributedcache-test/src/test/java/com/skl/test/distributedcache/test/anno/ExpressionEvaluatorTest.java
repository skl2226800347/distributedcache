package com.skl.test.distributedcache.test.anno;

import com.skl.distributedcache.anno.method.CacheInvokeContext;
import com.skl.distributedcache.anno.method.ExpressionEvaluator;
import com.skl.distributedcache.test.UserDemoManager;
import com.skl.distributedcache.test.UserDemoService;
import org.junit.Test;

import java.lang.reflect.Method;

public class ExpressionEvaluatorTest {
    @Test
    public void expressValue()throws Exception{
        UserDemoManager userDemoManager = new UserDemoManager();
        String scritp="#id";
        Method method = userDemoManager.getClass().getMethod("get",String.class);
        ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(scritp,method);
        CacheInvokeContext cacheInvokeContext = new CacheInvokeContext();
        String[] args = new String[1];
        args[0]="123";
        cacheInvokeContext.setArgs(args);
        Object result = expressionEvaluator.apply(cacheInvokeContext);
        System.out.println(result);
    }
}
