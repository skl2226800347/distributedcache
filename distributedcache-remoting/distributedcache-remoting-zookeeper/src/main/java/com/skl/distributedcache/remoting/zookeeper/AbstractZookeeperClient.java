package com.skl.distributedcache.remoting.zookeeper;

import com.skl.distributedcache.core.utils.NetUtil;
import com.skl.distributedcache.core.utils.StringUtils;
import com.skl.distributedcache.remoting.api.listener.DataListener;
import com.skl.distributedcache.remoting.api.param.RemotingParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public abstract class AbstractZookeeperClient implements ZookeeperClient {
    protected List<String> subscribes = new ArrayList<>();
    protected ZookeeperConfig zookeeperConfig;

    public AbstractZookeeperClient(ZookeeperConfig zookeeperConfig){
        Objects.requireNonNull(zookeeperConfig,"zookeeperConfig is null");
        this.zookeeperConfig = zookeeperConfig;
    }

    @Override
    public boolean isClose() {
        return false;
    }

    @Override
    public void publish(String path, RemotingParam param) {
        if(!isExists(path)){
            create(path,false);
        }
        if(StringUtils.isEmpty(param.getPublicIp())){
            param.setPublicIp(NetUtil.getIp());
            param.setPublishPort(this.zookeeperConfig.getPublicPort());
        }
        doPublish(path,param);
    }
    protected abstract void doPublish(String path,RemotingParam remotingParam);

    @Override
    public void subscribe(String path, DataListener listener) {
        subscribe(path,listener,null);
    }

    @Override
    public void subscribe(String path, DataListener dataListener, Executor executor) {
        Objects.requireNonNull(path,"path is no null");
        Objects.requireNonNull(path,"dataListener is not null");
        if(isSubscribe(path)){
            return;
        }
        if(!isExists(path)){
            create(path,false);
        }
        doSubscribe(path,dataListener,executor);
    }

    @Override
    public void create(String path, boolean ephemeral) {
        if(ephemeral){
            createEphemeral(path);
        } else {
            createPersistent(path);
        }
    }

    protected abstract void createEphemeral(String path);

    protected abstract void createPersistent(String path);

    protected abstract void doSubscribe(String path, DataListener dataListener, Executor executor);

    @Override
    public void delete(String path) {
        subscribes.remove(path);
        doDelete(path);
    }
    protected abstract void doDelete(String path);
    protected boolean isSubscribe(String path){
        if (subscribes.contains(path)){
            return true;
        }
        return false;
    }



}
