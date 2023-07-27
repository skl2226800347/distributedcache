package com.skl.distributedcache.starter.springboot.remoting;

import com.skl.distributedcache.anno.support.GlobalCacheConfig;
import com.skl.distributedcache.core.utils.StringUtils;
import com.skl.distributedcache.remoting.api.AbstractClientBuilder;
import com.skl.distributedcache.remoting.api.ClientBuilder;
import com.skl.distributedcache.starter.springboot.AutoConfigureBeans;
import com.skl.distributedcache.starter.springboot.ConfigTree;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractRemotingAutoInit implements InitializingBean {
    private String[] remoteTypes;
    private boolean inited = false;

    @Autowired
    protected AutoConfigureBeans autoConfigureBeans;
    @Autowired
    private ConfigurableEnvironment environment;



    protected abstract ClientBuilder initClientBuilder(ConfigTree configTree,String prefix);

    public AbstractRemotingAutoInit(String ...remoteTypes){
        Objects.requireNonNull(remoteTypes,"remote types is not null");
        Assert.isTrue(remoteTypes.length >0,"remote type length greater than 0 ");
        this.remoteTypes = remoteTypes;
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        if(!inited){
            process("distributecache.remoting.",autoConfigureBeans.getClientBuilders());
            inited = true;
        }
    }

    protected void process(String prefix, Map<String, ClientBuilder> clientBuilders){
        ConfigTree configTree = new ConfigTree(environment,prefix);
        Map<String,Object> properties = configTree.getProperties();
        String remotingType = String.valueOf(properties.get("type"));
        ClientBuilder clientBuilder = initClientBuilder(configTree,prefix);
        clientBuilders.put(remotingType,clientBuilder);
        subscribe(clientBuilder);
        if(autoConfigureBeans.getClient() == null) {
            autoConfigureBeans.setClient(clientBuilder.builderClient());
        }
        if(autoConfigureBeans.getClientBuilder() == null){
            autoConfigureBeans.setClientBuilder(clientBuilder);
        }
    }




    protected  void parseConfig(ConfigTree ct,ClientBuilder clientBuilder){
        AbstractClientBuilder abstractClientBuilder = (AbstractClientBuilder)clientBuilder;
        String address = ct.getProperty("address");
        if(StringUtils.isNotEmpty(address )) {
            abstractClientBuilder.getConfig().setAddress(address);
        }
        String connectionTimeoutMs = ct.getProperty("connectionTimeoutMs");
        if(StringUtils.isNotEmpty(connectionTimeoutMs)){
            abstractClientBuilder.getConfig().setConnectionTimeoutMs(Integer.parseInt(connectionTimeoutMs));
        }
        String maxWaitTime = ct.getProperty("maxWaitTime");
        if(StringUtils.isNotEmpty(maxWaitTime)){
            abstractClientBuilder.getConfig().setMaxWaitTime(Integer.parseInt(maxWaitTime));
        }
        String sessionTimeoutMs = ct.getProperty("sessionTimeoutMs");
        if (StringUtils.isNotEmpty(sessionTimeoutMs)){
            abstractClientBuilder.getConfig().setSessionTimeoutMs(Integer.parseInt(sessionTimeoutMs));
        }

    }

    protected  void subscribe(ClientBuilder clientBuilder){

    }


}
