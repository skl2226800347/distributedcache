package com.skl.distributedcache.remoting.zookeeper;

import com.skl.distributedcache.remoting.api.AbstractClientBuilder;
import com.skl.distributedcache.remoting.api.Client;
import com.skl.distributedcache.remoting.api.RemotingConfig;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class ZookeeperClientBuider extends AbstractClientBuilder {

    protected ConcurrentMap<String, ZookeeperClient> zookeeperClientMap = new ConcurrentHashMap<>();


    @Override
    public Client builderClient() {
        Objects.requireNonNull(this.getConfig(),"config  not null");
        Objects.requireNonNull(this.getConfig().getAddress(),"address param not null");
        String address = getConfig().getAddress();
        ZookeeperClient zookeeperClient = fetchZookeeperClientAndUpdateCache(address);
        if (zookeeperClient != null){
            return zookeeperClient;
        }
        synchronized (this){
            zookeeperClient = fetchZookeeperClientAndUpdateCache(address);
            if (zookeeperClient != null){
                return zookeeperClient;
            }
            zookeeperClient = createZookeeperClient((ZookeeperConfig)config);
            writeToCache(address,zookeeperClient);
        }
        return zookeeperClient;
    }

    @Override
    public RemotingConfig getConfig() {
        if(this.config == null){
            this.config = new ZookeeperConfig();
        }
        return this.config;
    }


    private ZookeeperClient fetchZookeeperClientAndUpdateCache(String address){
        ZookeeperClient zookeeperClient = zookeeperClientMap.get(address);
        if(zookeeperClient != null && !zookeeperClient.isClose()){
            writeToCache(address,zookeeperClient);
            return zookeeperClient;
        }
        return null;
    }
    private void writeToCache(String address ,ZookeeperClient zookeeperClient){
        zookeeperClientMap.put(address,zookeeperClient);
    }
    protected abstract ZookeeperClient createZookeeperClient(ZookeeperConfig config);

    @Override
    public List<String> subscribePaths() {
        ZookeeperConfig zookeeperConfig =  (ZookeeperConfig)getConfig();
        return zookeeperConfig.getSubscribePathList();
    }
}
