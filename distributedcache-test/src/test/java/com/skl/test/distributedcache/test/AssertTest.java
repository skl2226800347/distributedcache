package com.skl.test.distributedcache.test;

import org.junit.Test;
import org.springframework.util.Assert;

public class AssertTest {
    @Test
    public void isTrue(){
        String value ="xx";
        Assert.isTrue(value.length()>0,"value length <= 0");
    }

    @Test
    public void isTrue2(){
        String value ="";
        Assert.isTrue(value.length()>0,"value length <= 0");
    }
}
