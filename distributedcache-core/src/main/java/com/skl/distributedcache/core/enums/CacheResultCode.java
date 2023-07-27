package com.skl.distributedcache.core.enums;

public enum CacheResultCode {
    SUCCESS("1","success"),
    NOT_EXIST("2","not exists"),
    UPDATE_CACHE_FAIL("3","update cache failure"),
    KEY_IS_NULL("4","key is null");
    private String code;
    String msg;
    CacheResultCode(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
