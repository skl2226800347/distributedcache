package com.skl.distributedcache.starter.springboot.remoting.zookeeper;

import com.skl.distributedcache.core.utils.StringUtils;
import com.skl.distributedcache.remoting.api.ClientBuilder;
import com.skl.distributedcache.remoting.zookeeper.curator.CuratorZookeeperClientBuilder;
import com.skl.distributedcache.remoting.zookeeper.curator.CuratorZookeeperConfig;
import com.skl.distributedcache.starter.springboot.ConfigTree;
import com.skl.distributedcache.starter.springboot.remoting.DistributecacheRemotingCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@Conditional(CuratorZookeeperAutoConfiguration.CuratorZookeeperCondition.class)
public class CuratorZookeeperAutoConfiguration {
    private static final String REMOTING_TYPE="zookeeper-curator";

    private static final String CURATOR_ZOOKEEPER_REMOTING_AUTO_INIT="curatorZookeeperRemotingAutoInit";

    @Bean(name = CURATOR_ZOOKEEPER_REMOTING_AUTO_INIT)
    public CuratorZookeeperRemotingAutoInit curatorZookeeperRemotingAutoInit(){
        CuratorZookeeperRemotingAutoInit curatorZookeeperRemotingAutoInit = new CuratorZookeeperRemotingAutoInit();
        return curatorZookeeperRemotingAutoInit;
    }

    public static class CuratorZookeeperRemotingAutoInit extends ZookeeperRemotingAutoInit{
        public CuratorZookeeperRemotingAutoInit(){
            super(REMOTING_TYPE);
        }
        @Override
        protected ClientBuilder initClientBuilder(ConfigTree configTree, String prefix) {
            CuratorZookeeperClientBuilder builder = CuratorZookeeperClientBuilder.createCuratorZookeeperClientBuilder();
            parseConfig(configTree,builder);
            return builder;
        }

        @Override
        protected void parseConfig(ConfigTree ct, ClientBuilder clientBuilder) {
           super.parseConfig(ct,clientBuilder);
           CuratorZookeeperClientBuilder curatorZookeeperClientBuilder = (CuratorZookeeperClientBuilder)clientBuilder;
           CuratorZookeeperConfig config=(CuratorZookeeperConfig)curatorZookeeperClientBuilder.getConfig();
           String sleepMsBetweenRetries = ct.getProperty("sleepMsBetweenRetries");
           if(StringUtils.isNotEmpty(sleepMsBetweenRetries)){
               config.setSleepMsBetweenRetries(Integer.parseInt(sleepMsBetweenRetries));
           }
        }
    }

    public static class CuratorZookeeperCondition extends DistributecacheRemotingCondition {
        public CuratorZookeeperCondition(){
            super(REMOTING_TYPE);
        }
    }
}
