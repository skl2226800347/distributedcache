package com.skl.test.distributedcache.core.bytecode;

import com.skl.test.distributedcache.core.demo.DemoManager;
import org.junit.Test;

public class WrapperDemo_V2_Test {
    @Test
    public void sayV2()throws Exception{
        DemoManager demoManager = new DemoManager();
        WrapperDemo wrapper = WrapperDemo.getWrapper(demoManager.getClass());
        Class[] types= {String.class};
        Object[] values={"你好朋友"};
        wrapper.invokeMethod(demoManager,"sayV2",types,values);
    }
}
