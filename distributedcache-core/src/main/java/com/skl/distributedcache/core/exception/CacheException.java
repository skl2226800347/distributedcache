package com.skl.distributedcache.core.exception;

public class CacheException extends RuntimeException{
    public CacheException(String message,Throwable t){
        super(message,t);
    }
    public CacheException(Throwable t){
        super(t);
    }
    public CacheException(String message){
        super(message);
    }
    public CacheException(){

    }
}
