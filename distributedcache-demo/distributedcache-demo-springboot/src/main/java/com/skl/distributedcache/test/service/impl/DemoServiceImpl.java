package com.skl.distributedcache.test.service.impl;

import com.skl.distributedcache.anno.api.CacheInvalidate;
import com.skl.distributedcache.anno.api.CacheType;
import com.skl.distributedcache.anno.api.CacheUpdate;
import com.skl.distributedcache.anno.api.Cached;
import com.skl.distributedcache.test.service.DemoService;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl extends BaseService implements DemoService {

    @Override
    @Cached(area = "default",name = "name",cacheType = CacheType.LOCAL,key="#id")
    public String get(String id) {
        return "返回结果:"+id;
    }



    @Override
    @CacheInvalidate(area = "default",name="name",key="#id")
    public void remove(String id) {
        System.out.println("remove   id"+id);
    }

    @Override
    @CacheUpdate(area = "default",key="#id",name = "name",value = "args[1]")
    public void update(String id,String value) {
        System.out.println("update   id"+id);
    }
}
