package com.skl.distributedcache.remoting.api.param;

import com.alibaba.fastjson.JSONObject;
import com.skl.distributedcache.remoting.api.constants.RemotingConstants;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RemotingParam<K,V> implements Serializable {
    private String area;
    private String cacheName;
    private int dataEventType;
    private String publicIp;
    private int publishPort;
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

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        Objects.requireNonNull(publicIp,"public ip is null");
        this.publicIp = publicIp;
    }

    public int getPublishPort() {
        return publishPort;
    }

    public void setPublishPort(int publishPort) {
        this.publishPort = publishPort;
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

    public String publicHost(){
        StringBuilder publicHost = new StringBuilder();
        publicHost.append(getPublicIp()).append(RemotingConstants.DEFAULT_SEPARAT_SYSTEM).append(publishPort);
        return publicHost.toString();
    }
}
