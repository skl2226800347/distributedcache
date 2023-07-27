package com.skl.distributedcache.core.support;

import org.springframework.core.ConfigurableObjectInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SpringJavaValueDecoder extends JavaValueDecoder{
    public static SpringJavaValueDecoder INSTANCE = new SpringJavaValueDecoder(true);
    private boolean useIdentityNumber;

    public SpringJavaValueDecoder(boolean useIentityNumber) {
        super(useIentityNumber);
    }

    @Override
    protected ObjectInputStream buildObjectInputStream(ByteArrayInputStream bais) throws IOException {
        return new ConfigurableObjectInputStream(bais,Thread.currentThread().getContextClassLoader());
    }
}
