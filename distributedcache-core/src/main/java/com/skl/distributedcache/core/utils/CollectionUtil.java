package com.skl.distributedcache.core.utils;

import java.util.Collection;

public class CollectionUtil {

    public static final boolean isEmpty(Collection collection){
        return !isNotEmpty(collection);
    }
    public static final boolean isNotEmpty(Collection collection){
        if (collection == null || collection.size()<=0){
            return false;
        }
        return true;
    }
}
