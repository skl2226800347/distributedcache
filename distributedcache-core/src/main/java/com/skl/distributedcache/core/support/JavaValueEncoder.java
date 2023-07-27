package com.skl.distributedcache.core.support;

import com.skl.distributedcache.core.exception.CacheEncoderException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;

public class JavaValueEncoder extends AbstractValueEncoder{


    private final static int INIT_BUF_SIZE=256;
    private static final int IDENTITY_NUMBER=0x4A953A80;

    public static final JavaValueEncoder INSTANCE = new JavaValueEncoder(true);

    private static ThreadLocal<WeakReference<ByteArrayOutputStream>> THREAD_LOCAL
            =ThreadLocal.withInitial(()->new WeakReference<ByteArrayOutputStream>(new ByteArrayOutputStream(INIT_BUF_SIZE)));
    private boolean useIdentityNumber;

    public JavaValueEncoder(boolean useIdentityNumber){
        super(useIdentityNumber);
        this.useIdentityNumber = useIdentityNumber;
    }

    @Override
    public byte[] apply(Object o) {
        try{
            WeakReference<ByteArrayOutputStream> ref = THREAD_LOCAL.get();
            ByteArrayOutputStream baos = ref.get();
            if (baos == null){
                baos = new ByteArrayOutputStream(INIT_BUF_SIZE);
                THREAD_LOCAL.set(new WeakReference<>(baos));
            }
            try {

                if(useIdentityNumber){
                    baos.write((IDENTITY_NUMBER>>24) & 0xFF);
                    baos.write((IDENTITY_NUMBER>>16) & 0xFF);
                    baos.write((IDENTITY_NUMBER)>>8 & 0xFF);
                    baos.write(IDENTITY_NUMBER & 0xFF);
                }

                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(o);
                oos.flush();

                return baos.toByteArray();
            }finally {
                baos.reset();
            }
        }catch (IOException e){
            throw new CacheEncoderException(e.getMessage(),e);
        }
    }
}
