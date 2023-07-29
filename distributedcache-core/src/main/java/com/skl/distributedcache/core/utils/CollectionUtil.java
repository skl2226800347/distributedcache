package com.skl.distributedcache.core.utils;

import java.util.Collection;
import java.util.Map;

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

    public static final boolean isEmpty(Map map){
        return !isNotEmpty(map);
    }

    public static final boolean isNotEmpty(Map map){
        if (map == null || map.size()<=0){
            return false;
        }
        return true;
    }
}
