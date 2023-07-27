package com.skl.test.distributedcache.test;
import com.skl.distributedcache.test.service.DemoService;
import org.junit.Test;
import javax.annotation.Resource;
public class DemoServiceTest extends AbstractTestCase{
    @Resource
    private DemoService demoService;
    @Test
    public void get(){
        String id="xxx";
        String value = demoService.get(id);
        System.out.println(value);
        value = demoService.get(id);
        System.out.println(value);
    }
}
