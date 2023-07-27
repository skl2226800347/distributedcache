package com.skl.distributedcache.core.result;

import com.skl.distributedcache.core.CacheValueHolder;
import com.skl.distributedcache.core.enums.CacheResultCode;

import javax.xml.ws.Holder;

public class CacheGetResult<V> extends CacheResult {
    public static final CacheGetResult NOT_EXIST = new CacheGetResult( CacheResultCode.NOT_EXIST);

    public CacheGetResult(Throwable e){
        super(e);
    }
    public CacheGetResult(CacheResultCode cacheResultCode){
        super(cacheResultCode);
    }

    public static final CacheGetResult createSuccess(CacheValueHolder holder){
        CacheGetResult result = new CacheGetResult(CacheResultCode.SUCCESS);
        result.setHolder(holder);
        return result;
    }


    V value;

    CacheValueHolder<V> holder;

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public CacheValueHolder<V> getHolder() {
        return holder;
    }

    public void setHolder(CacheValueHolder<V> holder) {
        this.holder = holder;
        this.setValue(holder.getValue());
    }
}
