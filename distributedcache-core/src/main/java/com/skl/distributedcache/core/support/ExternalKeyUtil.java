package com.skl.distributedcache.core.support;

import com.skl.distributedcache.core.exception.CacheException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExternalKeyUtil {
    private static final String CHARSET = "UTF-8";
    public static final byte[] buildKeyAfterConvert(String prefix,Object key)throws IOException{
        if(key == null){
            throw new CacheException("key is null");
        }
        byte[] keyBytesWithOutPrefix = null;
        if (key instanceof String) {
            keyBytesWithOutPrefix = key.toString().getBytes(CHARSET);
        }else if(key instanceof byte[]){
            keyBytesWithOutPrefix=(byte[]) key;
        }else if (key instanceof Boolean){
            keyBytesWithOutPrefix = key.toString().getBytes(CHARSET);
        }else if(key instanceof Number){
            keyBytesWithOutPrefix = (key.getClass().getSimpleName()+key).getBytes(CHARSET);
        }else if(key instanceof Date){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss,SSS");
            keyBytesWithOutPrefix = (key.getClass().getSimpleName()+sdf.format((Date)key)).getBytes(CHARSET);
        } else if(key instanceof Serializable){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(key);
            oos.close();
            baos.close();
            keyBytesWithOutPrefix = baos.toByteArray();
        } else {
            throw new CacheException("can not convert class:"+key.getClass());
        }
        byte[] prefixBytes = prefix.getBytes(CHARSET);
        byte[] keyBytes =new byte[prefix.length()+keyBytesWithOutPrefix.length];
        System.arraycopy(prefixBytes,0,keyBytes,0,prefixBytes.length);
        System.arraycopy(keyBytesWithOutPrefix,0,keyBytes,prefixBytes.length,keyBytesWithOutPrefix.length);
        return keyBytes;
    }
}
