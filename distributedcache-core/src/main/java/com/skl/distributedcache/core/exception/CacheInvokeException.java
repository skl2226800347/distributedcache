package com.skl.distributedcache.core.exception;

public class CacheInvokeException extends CacheException{
    public CacheInvokeException(String message,Throwable t){
        super(message,t);
    }
    public CacheInvokeException(String message){
        super(message);
    }
}
