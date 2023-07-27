package com.skl.test.distributedcache.test.remoting;
import com.skl.distributedcache.remoting.api.DataEvent;
import com.skl.distributedcache.remoting.api.listener.DataListener;
import com.skl.distributedcache.remoting.zookeeper.curator.CuratorZookeeperClient;
import com.skl.distributedcache.remoting.zookeeper.curator.CuratorZookeeperConfig;

public class CuratorZookeeperClientSubscribe {
    private static final CuratorZookeeperClient client;
    static {
        CuratorZookeeperConfig config = new CuratorZookeeperConfig();
        config.setAddress("127.0.0.1:2181");
        config.setSubscribeRoot("/distributecache");
        client = new CuratorZookeeperClient(config);
    }
    public static void main(String[]args)throws Exception{
        String subscribePath="/distributecache/default";
        DataListener dataListener =new DataListener() {
            @Override
            public void dataEvent(DataEvent dataEvent) {
                System.out.println("dataEvent path:"+dataEvent.getPath());
                System.out.println("dataEvent data:"+new String(dataEvent.getData()));
            }
        };
       client.subscribe(subscribePath,dataListener);
       System.out.println("订阅成功");
       Thread.sleep(Integer.MAX_VALUE);
    }
}
