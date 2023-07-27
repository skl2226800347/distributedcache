package com.skl.distributedcache.starter.springboot;

import com.skl.distributedcache.core.CacheBuilder;
import com.skl.distributedcache.core.exception.CacheException;
import com.skl.distributedcache.external.redis.RedisCacheBuilder;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.Protocol;
import redis.clients.util.Pool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author skl
 */
@Configuration
@Conditional(RedisAutoConfiguration.RedisCondition.class)
public class RedisAutoConfiguration {
    public static final String REDIS_CACHE_AUTO_INIT_NAME="redisCacheAutoInit";
    public static final String CACHE_TYPE="redis";
    private static final int INVALID_PORT =0;


    @Bean(name = REDIS_CACHE_AUTO_INIT_NAME)
    public RedisCacheAutoInit redisCacheAutoInit(){
        RedisCacheAutoInit redisCacheAutoInit = new RedisCacheAutoInit();
        return redisCacheAutoInit;
    }

    public static class RedisCacheAutoInit extends AbstractExternalCacheAutoInit{
        public RedisCacheAutoInit(){
            super(CACHE_TYPE);
        }
        @Override
        protected CacheBuilder initCacheBuilder(ConfigTree configTree, String cacheAreaWithPrefix) {
            Pool<Jedis> jedisPool = parseJedisPool(configTree);
            boolean readFromSlave = Boolean.valueOf(configTree.getProperty("readFromSlave","False"));

            Pool<Jedis>[] slaveJedisPools = null;
            int[] slaveReadWeights=null;
            ConfigTree slaveConfigTrees = configTree.subTree("slaves.");
            Set<String> slaveNameSet = slaveConfigTrees.getDirectChildrenKeys();
            if(slaveNameSet.size() >0){
                slaveJedisPools = new Pool[slaveNameSet.size()];
                slaveReadWeights = new int[slaveNameSet.size()];
                int index =0;
                for(String slaveName: slaveNameSet){
                    ConfigTree slaveConfigTree = slaveConfigTrees.subTree(slaveName+".");
                    slaveJedisPools[index] = parseJedisPool(slaveConfigTree);
                    slaveReadWeights[index] = Integer.parseInt(slaveConfigTree.getProperty("slavePoolWeight","200"));
                    index++;
                }
            }
            RedisCacheBuilder redisCacheBuilder = RedisCacheBuilder.createRedisCacheBuilder()
                    .jedisPool(jedisPool)
                    .readFromSlave(readFromSlave)
                    .slaveJedisPools(slaveJedisPools)
                    .slaveReadWeights(slaveReadWeights);

            parseGenernalCacheConfig(configTree,redisCacheBuilder);
            return redisCacheBuilder;
        }

        private Pool<Jedis> parseJedisPool(ConfigTree configTree){
            GenericObjectPoolConfig poolConfig = parsePoolConfig(configTree);
            String host = configTree.getProperty("host",null);
            int  port = Integer.valueOf(configTree.getProperty("port","0"));
            int timeout = Integer.valueOf(configTree.getProperty("timeout", String.valueOf(Protocol.DEFAULT_TIMEOUT)));
            String passowrd = configTree.getProperty("password",null);
            int database = Integer.valueOf(configTree.getProperty("database",String.valueOf(Protocol.DEFAULT_DATABASE)));
            String clientName = configTree.getProperty("clientName",null);
            boolean ssl = Boolean.parseBoolean(configTree.getProperty("ssl","False"));

            String sentinels = configTree.getProperty("sentinels",null);
            String masterName = configTree.getProperty("masterName",null);

            Pool<Jedis> pool = null;
            if(sentinels == null){
                Objects.requireNonNull(host,"host is required");
                if(port == INVALID_PORT){
                    throw new CacheException("port is required");
                }
                pool =  new JedisPool(poolConfig,host,port,timeout,passowrd,database,clientName,ssl);
            } else {
                Objects.requireNonNull(masterName,"masterName is required");
                String[] sentinelArray = sentinels.split(",");
                Set<String> sentinelSet = new HashSet();
                for(String sentinel : sentinelArray){
                    sentinelSet.add(sentinel);
                }
                pool = new JedisSentinelPool(masterName,sentinelSet,poolConfig,timeout,passowrd,database,clientName);
            }
            return pool;
        }
        private GenericObjectPoolConfig parsePoolConfig(ConfigTree configTree){

            try {
                if (ClassUtils.isPresent("org.springframework.boot.context.properties.bind.Binder", this.getClass().getClassLoader())) {
                    Class binderClass = Class.forName("org.springframework.boot.context.properties.bind.Binder");
                    Class bindableClass = Class.forName("org.springframework.boot.context.properties.bind.Bindable");
                    Class bindResultClass = Class.forName("org.springframework.boot.context.properties.bind.BindResult");
                    String prefix = configTree.subTree("poolConfig").getPrefix().toLowerCase();
                    Method getMethodOnBinder = binderClass.getMethod("get", Environment.class);
                    Method getMethodOnBindResult = bindResultClass.getMethod("get");

                    Method bindMethod = binderClass.getMethod("bind",String.class,bindableClass );
                    Method ofMethod = bindableClass.getMethod("of",Class.class );

                    Object binder = getMethodOnBinder.invoke(null,environment);
                    Object bindable = ofMethod.invoke(null,GenericObjectPoolConfig.class);
                    Object bindResult = bindMethod.invoke(binder,prefix,bindable);

                    return (GenericObjectPoolConfig)getMethodOnBindResult.invoke(bindResult);
                } else {
                    GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
                    Map<String,Object> props = configTree.subTree("poolConfig.").getProperties();
                    Class relaxedDataBinderClass = Class.forName("org.springframework.boot.bind.RelaxedDataBinder");
                    Class mutablePropertyValuesClass =Class.forName("org.springframework.beans.MutablePropertyValues");

                    Constructor relaxedDataBinderConstructor = relaxedDataBinderClass.getConstructor(Object.class);
                    Constructor mutablePropertyValuesConstructor = mutablePropertyValuesClass.getConstructor(Map.class);

                    Method bindMethod = relaxedDataBinderClass.getMethod("bind",PropertyValues.class);

                    Object relaxedDataBinder = relaxedDataBinderConstructor.newInstance(poolConfig);
                    bindMethod.invoke(relaxedDataBinder,mutablePropertyValuesConstructor.newInstance(props));
                    return poolConfig;
                }
            }catch (Throwable e){
                e.printStackTrace();
                throw new CacheException(e.getMessage());
            }
        }
    }
    public static class RedisCondition extends DistributecacheCondition{
        public RedisCondition(){
            super(CACHE_TYPE);
        }
    }
}
