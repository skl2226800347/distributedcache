package com.skl.distributedcache.remoting.api;

public class RemotingConfig {
    private static final int DEFAULT_CONNECTION_TIMEOUT_MS=10000;
    private static final int DEFAULT_MAX_WAIT_TIME=50000;
    private static final int DEFAULT_SESSION_TIMEOUT_MS=10000;
    private String address;
    private int connectionTimeoutMs = DEFAULT_CONNECTION_TIMEOUT_MS;
    private int maxWaitTime =DEFAULT_MAX_WAIT_TIME;
    private int sessionTimeoutMs =DEFAULT_SESSION_TIMEOUT_MS;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getConnectionTimeoutMs() {
        return connectionTimeoutMs;
    }

    public void setConnectionTimeoutMs(int connectionTimeoutMs) {
        this.connectionTimeoutMs = connectionTimeoutMs;
    }

    public int getMaxWaitTime() {
        return maxWaitTime;
    }

    public void setMaxWaitTime(int maxWaitTime) {
        this.maxWaitTime = maxWaitTime;
    }

    public int getSessionTimeoutMs() {
        return sessionTimeoutMs;
    }

    public void setSessionTimeoutMs(int sessionTimeoutMs) {
        this.sessionTimeoutMs = sessionTimeoutMs;
    }
}
