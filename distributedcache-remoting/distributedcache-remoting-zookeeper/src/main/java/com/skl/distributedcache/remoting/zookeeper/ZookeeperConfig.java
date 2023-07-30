package com.skl.distributedcache.remoting.zookeeper;

import com.skl.distributedcache.remoting.api.RemotingConfig;
import com.skl.distributedcache.remoting.api.constants.RemotingConstants;

import java.util.List;

public class ZookeeperConfig extends RemotingConfig {
    private static final String SUBSCRIBE_ROOT="/distributecache";
    private String subscribeRoot = SUBSCRIBE_ROOT;
    private int publicPort = RemotingConstants.DEFAULT_PUBLIC_PORT;
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

    public int getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(int publicPort) {
        this.publicPort = publicPort;
    }
}
