package com.skl.distributedcache.external.redis;

import com.skl.distributedcache.core.external.AbstractExternalCacheBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public class RedisCacheBuilder<T extends AbstractExternalCacheBuilder<T>> extends AbstractExternalCacheBuilder<T> {
    public static class RedisCacheBuilderImpl extends RedisCacheBuilder<RedisCacheBuilderImpl>{

    }
    @Override
    public RedisCacheConfig getCacheConfig() {
        if(cacheConfig == null){
            cacheConfig = new RedisCacheConfig();
        }
        return (RedisCacheConfig)cacheConfig;
    }
    public RedisCacheBuilder(){
        buildFunc(c->{
            return new RedisCache((RedisCacheConfig) c);
        });
    }
    public static final RedisCacheBuilderImpl createRedisCacheBuilder(){
        RedisCacheBuilderImpl redisCacheBuilderImpl = new RedisCacheBuilderImpl();
        return redisCacheBuilderImpl;
    }

    public  T jedisPool(Pool<Jedis> jedisPool){
        getCacheConfig().setJedisPool(jedisPool);
        return self();
    }

    public T slaveReadWeights(int[] slaveReadWeights){
        getCacheConfig().setSlaveReadWeights(slaveReadWeights);
        return self();
    }

    public T readFromSlave(boolean readFromSlave){
        getCacheConfig().setReadFromSlave(readFromSlave);
        return self();
    }

    public T slaveJedisPools(Pool<Jedis>[] slaveJedisPools){
        getCacheConfig().setSlaveJedisPools(slaveJedisPools);
        return self();
    }

}
