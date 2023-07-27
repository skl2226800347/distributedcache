package com.skl.distributedcache.anno.api;

import java.util.function.Function;

public interface SerialPolicy {
    String JAVA="JAVA";

    Function<Object,byte[]> encoder();
    Function<byte[],Object> decoder();
}
