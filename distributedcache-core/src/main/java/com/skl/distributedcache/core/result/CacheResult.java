package com.skl.distributedcache.core.result;
import com.skl.distributedcache.core.enums.CacheResultCode;

public class CacheResult {
    public static final String ERROR_PARAM="error param";
    public static final CacheResult FAIL_ERROR_PARAM=new CacheResult(CacheResultCode.FAIL,ERROR_PARAM);
    public static final CacheResult FAIL_WITHOUT_MSG = new CacheResult( CacheResultCode.FAIL, null);
    public static final CacheResult SUCCESS_WITHOUT_MSG = new CacheResult(CacheResultCode.SUCCESS,null);
    private CacheResultCode resultCode;
    private String msg;

    public CacheResult(CacheResultCode resultCode,String msg) {
        this.resultCode= resultCode;
        this.msg = msg;
    }

    public CacheResult(Throwable e){

    }

    public CacheResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(CacheResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public boolean isSuccess(){
        return getResultCode() ==CacheResultCode.SUCCESS;
    }
}
