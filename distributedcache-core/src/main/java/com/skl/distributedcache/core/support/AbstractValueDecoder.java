package com.skl.distributedcache.core.support;

import com.skl.distributedcache.core.exception.CacheEncoderException;

import java.util.Objects;
import java.util.function.Function;

public abstract class AbstractValueDecoder implements Function<byte[],Object> {
    private boolean useIdentityNumber;
    public AbstractValueDecoder(boolean useIdentityNumber){
        this.useIdentityNumber = useIdentityNumber;
    }
    protected abstract Object doApply(byte[] bytes) throws Exception;

    protected int parseHeader(byte[] bytes){
        int x = 0;
        x = x | (bytes[0] & 0xFF);
        x = x<<8;
        x = x | (bytes[1] & 0xFF);
        x = x<<8;
        x = x |(bytes[2] & 0xFF);
        x = x<<8;
        x = x |(bytes[3] & 0xFF);
        return x;
    }
    @Override
    public Object apply(byte[] bytes) {
        try {
            if (useIdentityNumber) {
                DecoderMap.registerBuilderInDecoder();
                int identityNumber = parseHeader(bytes);
                AbstractValueDecoder decoder = DecoderMap.getDecoder(identityNumber);
                Objects.requireNonNull(decoder,"no decoder for identityNumber:"+identityNumber);
                return decoder.doApply(bytes);
            } else {
                return doApply(bytes);
            }
        }catch (Exception e){
            throw new CacheEncoderException("decoder error",e);
        }
    }
}
