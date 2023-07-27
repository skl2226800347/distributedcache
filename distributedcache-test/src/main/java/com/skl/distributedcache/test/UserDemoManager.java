package com.skl.distributedcache.test;

import com.skl.distributedcache.anno.api.Cached;

public class UserDemoManager extends BaseUserDemoManager{
    @Cached(key="#id")
    public String get(String id){
        return id;
    }

    public void display(){
        System.out.println("display....");
    }

    public static String acuire(String str){
        return "return"+str;
    }
}
