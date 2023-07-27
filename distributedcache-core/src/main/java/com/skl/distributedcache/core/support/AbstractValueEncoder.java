package com.skl.distributedcache.core.support;

import java.util.function.Function;

public abstract class AbstractValueEncoder implements Function<Object,byte[]> {

    public AbstractValueEncoder(boolean useIdentityNumber){

    }

}
