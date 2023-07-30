package com.skl.test.distributedcache.core.util;

import com.skl.distributedcache.core.utils.NetUtil;
import org.junit.Test;

public class NetUtilTest {
    @Test
    public void getIp(){
        String ip = NetUtil.getIp();
        System.out.println(ip);
    }
}
