package com.skl.distributedcache.remoting.zookeeper;

import com.skl.distributedcache.remoting.api.RemotingConfig;

import java.util.List;

public class ZookeeperConfig extends RemotingConfig {
    private static final String SUBSCRIBE_ROOT="/distributecache";
    private String subscribeRoot = SUBSCRIBE_ROOT;
    private List<String> subscribePathList ;

    public String getSubscribeRoot() {
        return subscribeRoot;
    }

    public void setSubscribeRoot(String subscribeRoot) {
        this.subscribeRoot = subscribeRoot;
    }

    public List<String> getSubscribePathList() {
        return subscribePathList;
    }

    public void setSubscribePathList(List<String> subscribePathList) {
        this.subscribePathList = subscribePathList;
    }
}
