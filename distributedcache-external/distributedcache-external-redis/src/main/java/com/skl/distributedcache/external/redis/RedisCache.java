package com.skl.distributedcache.external.redis;

import com.skl.distributedcache.core.CacheValueHolder;
import com.skl.distributedcache.core.exception.CacheException;
import com.skl.distributedcache.core.external.AbstractExternalCache;
import com.skl.distributedcache.core.result.CacheGetResult;
import com.skl.distributedcache.core.result.CacheResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class RedisCache<K,V> extends AbstractExternalCache<K,V> {
    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);
    private Function<Object,byte[]> valueEncoder;
    private Function<byte[],Object> valueDecoder;
    private RedisCacheConfig cacheConfig;


    public RedisCache(RedisCacheConfig cacheConfig){
        super(cacheConfig);
        this.cacheConfig = cacheConfig;
        valueEncoder = cacheConfig.getValueEncoder();
        valueDecoder = cacheConfig.getValueDecoder();
    }

    @Override
    protected CacheGetResult<V> doGET(K key) {
        try(Jedis jedis = getJedisPool().getResource()) {
            byte[] value = jedis.get(buildKey(getCacheConfig().getKeyPrefix(), key));
            if (value == null) {
                return CacheGetResult.NOT_EXIST;
            }
            CacheValueHolder<V> holder = (CacheValueHolder) valueDecoder.apply(value);
            if (holder == null) {
                return CacheGetResult.NOT_EXIST;
            } else {
                return CacheGetResult.createSuccess(holder);
            }
        }catch (Throwable e){
            logger.error("doGET  e:{}",e);
            return new CacheGetResult(e);
        }
    }

    @Override
    protected CacheResult doPut(K key, V value) {
        try(Jedis jedis = getJedisPool().getResource()) {
            CacheValueHolder holder = CacheValueHolder.createCacheValueHolder(value);
            jedis.set(buildKey(getCacheConfig().getKeyPrefix(), key), valueEncoder.apply(holder));
            return CacheResult.createSuccess();
        }catch (Throwable e){
            logger.error("doPut e:{}",e);
            return new CacheGetResult(e);
        }
    }

    @Override
    protected CacheResult doRemove(K key) {
        try(Jedis jedis = getJedisPool().getResource()) {
            jedis.del(buildKey(getCacheConfig().getKeyPrefix(), key));
            return CacheResult.createSuccess();
        }
    }

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();
    private Pool<Jedis> getJedisPool(){
        if(!getCacheConfig().isReadFromSlave()){
            return getCacheConfig().getJedisPool();
        }
        int[] slaveReadWeights = getCacheConfig().getSlaveReadWeights();
        int index =getRandomIndex(slaveReadWeights);
        return getCacheConfig().getSlaveJedisPools()[index];
    }

    protected static int getRandomIndex(int[] weights){
        int sumWeigh=0;
        for (int weight : weights){
            sumWeigh+=weight;
        }
        int r = RANDOM.nextInt(sumWeigh);
        int  y = 0;
        for (int i=0;i<weights.length;i++){
            y+=weights[i];
            if(y > r ){
                return i;
            }
        }
        throw new CacheException("index failure");
    }
    @Override
    public RedisCacheConfig getCacheConfig() {
        return cacheConfig;
    }
}
