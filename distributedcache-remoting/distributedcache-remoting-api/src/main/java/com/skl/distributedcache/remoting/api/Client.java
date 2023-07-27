package com.skl.distributedcache.remoting.api;

import com.skl.distributedcache.remoting.api.listener.DataListener;
import com.skl.distributedcache.remoting.api.param.RemotingParam;

import java.util.concurrent.Executor;

public interface Client {
    void publish(String path,RemotingParam param);
    void subscribe(String path, DataListener dataListener);
    void subscribe(String path, DataListener dataListener, Executor executor);
    void delete(String path);
}
