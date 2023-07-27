package com.skl.distributedcache.core.support;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JavaValueDecoder extends AbstractValueDecoder{
    public static final  JavaValueDecoder INSTANCE = new JavaValueDecoder(true);
    public static final int IDENTITY_NUMBER=0x4A953A80;
    private boolean useIentityNumber;
    public JavaValueDecoder(boolean useIentityNumber){
        super(useIentityNumber);
        this.useIentityNumber = useIentityNumber;
    }


    @Override
    protected Object doApply(byte[] bytes) throws Exception {
        ByteArrayInputStream bais;
        if(useIentityNumber){
            bais = new ByteArrayInputStream(bytes,4,bytes.length-4);
        } else {
            bais = new ByteArrayInputStream(bytes);
        }
        ObjectInputStream objectInputStream = buildObjectInputStream(bais);
        return objectInputStream.readObject();
    }

    protected ObjectInputStream buildObjectInputStream(ByteArrayInputStream bais)throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(bais);
        return objectInputStream;
    }


}
