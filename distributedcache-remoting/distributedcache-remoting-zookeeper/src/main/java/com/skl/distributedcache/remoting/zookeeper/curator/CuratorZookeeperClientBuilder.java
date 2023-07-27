package com.skl.distributedcache.remoting.zookeeper.curator;

import com.skl.distributedcache.remoting.api.RemotingConfig;
import com.skl.distributedcache.remoting.zookeeper.ZookeeperClientBuider;
import com.skl.distributedcache.remoting.zookeeper.ZookeeperClient;
import com.skl.distributedcache.remoting.zookeeper.ZookeeperConfig;

public class CuratorZookeeperClientBuilder extends ZookeeperClientBuider {


    public static final CuratorZookeeperClientBuilder createCuratorZookeeperClientBuilder(){
        CuratorZookeeperClientBuilder curatorZookeeperClientBuilder = new CuratorZookeeperClientBuilder();
        curatorZookeeperClientBuilder.buildFunc(c->new CuratorZookeeperClient((CuratorZookeeperConfig)c));
        return curatorZookeeperClientBuilder;
    }
    @Override
    protected ZookeeperClient createZookeeperClient(ZookeeperConfig config) {
        return new CuratorZookeeperClient((CuratorZookeeperConfig)config);
    }

    @Override
    public RemotingConfig getConfig() {
        if(this.config == null){
            this.config = new CuratorZookeeperConfig();
        }
        return this.config;
    }
}
