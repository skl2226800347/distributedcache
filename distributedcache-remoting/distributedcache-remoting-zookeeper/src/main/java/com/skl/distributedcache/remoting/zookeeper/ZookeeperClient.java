package com.skl.distributedcache.remoting.zookeeper;

import com.skl.distributedcache.remoting.api.Client;

public interface ZookeeperClient extends Client {
    boolean isClose();
    boolean isExists(String path);
    void create(String path,boolean ephemeral);
}
