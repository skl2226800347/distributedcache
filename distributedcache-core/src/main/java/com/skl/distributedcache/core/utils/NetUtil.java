package com.skl.distributedcache.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetUtil {

    public static final String getIp(){
        try {
            String ip = InetAddress.getLocalHost().getHostAddress();
            return ip;
        }catch (UnknownHostException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
