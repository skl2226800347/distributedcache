package com.skl.distributedcache.core.result;

import com.skl.distributedcache.core.enums.CacheResultCode;

public class CacheResult {
    public static final CacheResult UPDATE_CACHE_FAIL = new CacheResult( CacheResultCode.UPDATE_CACHE_FAIL);
    public static final CacheResult KEY_IS_NULL = new CacheResult( CacheResultCode.KEY_IS_NULL);
    public static final CacheResult SUCCESS = new CacheResult( CacheResultCode.SUCCESS);
    private boolean success = false;
    private String code;
    private String msg;

    public CacheResult(CacheResultCode cacheResultCode) {
        if (CacheResultCode.SUCCESS.equals(cacheResultCode)){
            this.success = true;
            this.code = cacheResultCode.getCode();
        } else {
            this.success = false;
            this.code = cacheResultCode.getCode();
            this.msg = cacheResultCode.getMsg();
        }
    }

    public CacheResult(Throwable e){

    }

    public static final CacheResult createSuccess(){
        CacheResult result = new CacheResult(CacheResultCode.SUCCESS);
        return result;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
