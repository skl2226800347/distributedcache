package com.skl.distributedcache.starter.springboot.remoting.zookeeper;

import com.alibaba.fastjson.JSONObject;
import com.skl.distributedcache.anno.support.CacheConfigMap;
import com.skl.distributedcache.anno.support.DefaultCacheService;
import com.skl.distributedcache.core.Cache;
import com.skl.distributedcache.core.utils.CollectionUtil;
import com.skl.distributedcache.core.utils.StringUtils;
import com.skl.distributedcache.remoting.api.Client;
import com.skl.distributedcache.remoting.api.ClientBuilder;
import com.skl.distributedcache.remoting.api.DataEvent;
import com.skl.distributedcache.remoting.api.DataEventType;
import com.skl.distributedcache.remoting.api.listener.DataListener;
import com.skl.distributedcache.remoting.api.param.RemotingParam;
import com.skl.distributedcache.remoting.zookeeper.ZookeeperClientBuider;
import com.skl.distributedcache.remoting.zookeeper.ZookeeperConfig;
import com.skl.distributedcache.starter.springboot.ConfigTree;
import com.skl.distributedcache.starter.springboot.remoting.AbstractRemotingAutoInit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ZookeeperRemotingAutoInit extends AbstractRemotingAutoInit {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperRemotingAutoInit.class);

    @Autowired
    private CacheConfigMap cacheConfigMap;

    public ZookeeperRemotingAutoInit(String ... remoteTypes){
        super(remoteTypes);
    }
    @Override
    protected void parseConfig(ConfigTree ct, ClientBuilder clientBuilder) {
        super.parseConfig(ct, clientBuilder);
        ZookeeperClientBuider zookeeperClientBuider = (ZookeeperClientBuider)clientBuilder;
        ZookeeperConfig config =(ZookeeperConfig)zookeeperClientBuider.getConfig();
        String subscribeRoot = ct.getProperty("subscribeRoot");
        if(StringUtils.isNotEmpty(subscribeRoot)){
            config.setSubscribeRoot(subscribeRoot);
        }
        Set<String> areaSet = ct.getDirectChildrenKeys("distributecache.local.");
        if(CollectionUtil.isNotEmpty(areaSet)){
            List<String> subscribePathList = areaSet.stream().map(s->{
                StringBuilder subscribePath = new StringBuilder();
                subscribePath.append(config.getSubscribeRoot()).append("/").append(s);
                return subscribePath.toString();
            }).filter(s-> s != null).collect(Collectors.toList());
            config.setSubscribePathList(subscribePathList);
        }
    }

    @Override
    protected void subscribe(ClientBuilder clientBuilder) {
        ZookeeperClientBuider zookeeperClientBuider = (ZookeeperClientBuider)clientBuilder;
        ZookeeperConfig zookeeperConfig =(ZookeeperConfig)zookeeperClientBuider.getConfig();
        Client client = clientBuilder.builderClient();
        if(CollectionUtil.isNotEmpty(zookeeperConfig.getSubscribePathList())) {
            if(!cacheConfigMap.isContainLocaclCache()){
                return;
            }
            DefaultCacheService defaultCacheService = DefaultCacheService.getDefaultCacheService(cacheConfigMap,autoConfigureBeans.getLocalCacheBuilders(),autoConfigureBeans.getExternalCacheBuilders());
            if(defaultCacheService == null){
                return ;
            }
            for (String subscribePath : zookeeperConfig.getSubscribePathList()) {
                client.subscribe(subscribePath,getDataListener(defaultCacheService));
            }
        }
    }

    protected DataListener getDataListener(DefaultCacheService defaultCacheService){
        DataListener dataListener = new DataListener() {
            @Override
            public void dataEvent(DataEvent dataEvent) {
                if(dataEvent == null || dataEvent.getData() == null){
                    return;
                }
                String jsonStr = new String(dataEvent.getData());
                RemotingParam remotingParam = null;
                try {
                    remotingParam = JSONObject.parseObject(jsonStr, RemotingParam.class);
                }catch (Throwable e){
                    logger.error("dataEvent  jsonStr:{} error:{}",jsonStr,e);
                }
                if(remotingParam == null){
                    return;
                }
                if(StringUtils.isEmpty(remotingParam.getArea())){
                    return;
                }
                if(StringUtils.isEmpty(remotingParam.getCacheName())){
                    return ;
                }
                DataEventType dataEventType = DataEventType.getInstance(remotingParam.getDataEventType());
                if(dataEventType == null){
                    return;
                }
                String area = remotingParam.getArea();
                String cacheName = remotingParam.getCacheName();
                Cache cache= defaultCacheService.getCache(area,cacheName);
                if(cache == null){
                    return;
                }
                if(dataEventType == DataEventType.INVLIDATE) {
                    cache.remove(remotingParam.getKey());
                }else if(dataEventType == DataEventType.UPDATE){
                    cache.put(remotingParam.getKey(),remotingParam.getValue());
                }
            }
        };
        return dataListener;
    }
}
