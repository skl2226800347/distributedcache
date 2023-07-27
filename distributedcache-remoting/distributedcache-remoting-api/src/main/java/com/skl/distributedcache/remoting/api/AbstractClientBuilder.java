package com.skl.distributedcache.remoting.api;

import java.util.List;
import java.util.function.Function;

public abstract class AbstractClientBuilder implements ClientBuilder{
    protected RemotingConfig config;
    private Function<RemotingConfig,Client> buildFunc;

    protected void buildFunc(Function<RemotingConfig,Client> buildFunc){
        this.buildFunc = buildFunc;
    }

    public  abstract RemotingConfig getConfig();

    public  List<String> subscribePaths(){
        return null;
    }
}
