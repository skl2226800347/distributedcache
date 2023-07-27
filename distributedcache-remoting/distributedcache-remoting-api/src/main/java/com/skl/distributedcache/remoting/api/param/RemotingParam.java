package com.skl.distributedcache.remoting.api.param;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

public class RemotingParam<K,V> implements Serializable {
    private String area;
    private String cacheName;
    private int dataEventType;
    K key;
    V value;

    public byte[] toBytes(){
        return JSONObject.toJSONString(this).getBytes();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCacheName() {
        return cacheName;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }


    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public int getDataEventType() {
        return dataEventType;
    }

    public void setDataEventType(int dataEventType) {
        this.dataEventType = dataEventType;
    }
}
