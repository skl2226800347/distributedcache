package com.skl.distributedcache.remoting.api;

public enum  DataEventType {
    UPDATE(1,"UPDATE"),
    INVLIDATE(2,"INVALIDATE")
    ;
    private int type;
    private String msg;
    DataEventType(int type,String msg){
        this.type = type;
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public static DataEventType getInstance(int type){
        for(DataEventType dataEventType : values()){
            if (dataEventType.getType() == type){
                return dataEventType;
            }
        }
        return null;
    }
}
