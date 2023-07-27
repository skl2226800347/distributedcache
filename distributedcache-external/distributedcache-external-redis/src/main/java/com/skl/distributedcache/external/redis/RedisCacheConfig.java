package com.skl.distributedcache.external.redis;

import com.skl.distributedcache.core.external.ExternalCacheConfig;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

public class RedisCacheConfig<K,V> extends ExternalCacheConfig<K,V> {
    private Pool<Jedis> jedisPool;
    private int[] slaveReadWeights;
    private boolean readFromSlave;
    private Pool<Jedis>[] slaveJedisPools;
    public Pool<Jedis> getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(Pool<Jedis> jedisPool) {
        this.jedisPool = jedisPool;
    }


    public int[] getSlaveReadWeights() {
        return slaveReadWeights;
    }

    public void setSlaveReadWeights(int[] slaveReadWeights) {
        this.slaveReadWeights = slaveReadWeights;
    }

    public boolean isReadFromSlave() {
        return readFromSlave;
    }

    public void setReadFromSlave(boolean readFromSlave) {
        this.readFromSlave = readFromSlave;
    }

    public Pool<Jedis>[] getSlaveJedisPools() {
        return slaveJedisPools;
    }

    public void setSlaveJedisPools(Pool<Jedis>[] slaveJedisPools) {
        this.slaveJedisPools = slaveJedisPools;
    }
}
