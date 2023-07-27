package com.skl.test.distributedcache.test.remoting;
import com.skl.distributedcache.remoting.api.param.RemotingParam;
import com.skl.distributedcache.remoting.zookeeper.curator.CuratorZookeeperClient;
import com.skl.distributedcache.remoting.zookeeper.curator.CuratorZookeeperConfig;
import org.junit.Before;
import org.junit.Test;

public class CuratorZookeeperClientTest {
    CuratorZookeeperClient client;
    @Before
    public void init(){
        CuratorZookeeperConfig config = new CuratorZookeeperConfig();
        config.setAddress("127.0.0.1:2181");
        config.setSubscribeRoot("/distributecache");
        client = new CuratorZookeeperClient(config);
    }
    @Test
    public void publish_v1(){
        RemotingParam remotingParam = new RemotingParam();
        remotingParam.setValue("你好吗");
        remotingParam.setArea("default");
        remotingParam.setCacheName("name");
        remotingParam.setValue("3");
        client.publish("/distributecache/default",remotingParam);
    }
    @Test
    public void createEphemeral(){
        client.create("/distributecache/SKL",true);
    }
    @Test
    public void createEphemeralV2(){
        client.create("/distributecache/HERO",true);
    }
    @Test
    public void createEphemeralV3(){
        client.create("/distributecache",true);
    }
    @Test
    public void createEphemeralV4(){
        client.create("/distributecache/6",true);
        client.create("/distributecache/7",true);
    }

    @Test
    public void createPersistent(){
        client.create("/distributecache/default",false);
    }
    @Test
    public void createPersistentV2(){
        client.create("/distributecache",false);
    }

    @Test
    public void createPersistentV3(){
        client.create("/distributecache/SKL",false);
    }
}
