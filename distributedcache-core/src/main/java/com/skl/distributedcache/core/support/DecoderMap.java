package com.skl.distributedcache.core.support;

import java.util.HashMap;
import java.util.Map;

public class DecoderMap {
    private static Map<Integer,AbstractValueDecoder> DECODER_MAP = new HashMap<>();
    private static boolean inited = false;
    public static AbstractValueDecoder getDecoder(int identityNumber){
        return DECODER_MAP.get(identityNumber);
    }

    public static synchronized void register(int identityNumber,AbstractValueDecoder decoder){
        Map<Integer,AbstractValueDecoder> newMap = new HashMap<>();
        newMap.putAll(DECODER_MAP);
        newMap.put(identityNumber,decoder);
        DECODER_MAP = newMap;
    }


    static synchronized void registerBuilderInDecoder(){
        if(!inited) {
            DECODER_MAP.put(JavaValueDecoder.IDENTITY_NUMBER, defaultJavaDecoder());
            inited = true;
        }
    }

    public static JavaValueDecoder defaultJavaDecoder(){
        try {
            Class.forName("org.springframework.core.org.springframework.core");
            return SpringJavaValueDecoder.INSTANCE;
        }catch (ClassNotFoundException e){
            return JavaValueDecoder.INSTANCE;
        }
    }

}
