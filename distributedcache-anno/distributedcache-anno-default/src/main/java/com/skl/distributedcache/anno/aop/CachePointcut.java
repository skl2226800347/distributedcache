package com.skl.distributedcache.anno.aop;

import com.skl.distributedcache.anno.method.CacheInvokeConfig;
import com.skl.distributedcache.anno.support.CacheConfigMap;
import com.skl.distributedcache.anno.support.CacheParseUtil;
import jdk.internal.org.objectweb.asm.Type;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class CachePointcut extends StaticMethodMatcherPointcut implements ClassFilter {
    private String[] basePackages;
    private CacheConfigMap cacheConfigMap;

    public CachePointcut(String[] basePackages){
        this.basePackages = basePackages;
        setClassFilter(this);
    }
    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public void setCacheConfigMap(CacheConfigMap cacheConfigMap) {
        this.cacheConfigMap = cacheConfigMap;
    }

    @Override
    public boolean matches(Class<?> aClass) {
        return matchImpl(aClass);
    }

    private boolean matchImpl(Class clazz) {
        if(matchThis(clazz)){
            return true;
        }
        Class[] itf = clazz.getInterfaces();
        if(itf != null) {
            for(Class it:itf){
                if(matchThis(it)){
                    return true;
                }
            }
        }
        Class parentClazz = clazz.getSuperclass();
        if (parentClazz != null) {
            if(matchThis(parentClazz)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return matchImpl(method,aClass);
    }


    private boolean matchImpl(Method method ,Class clazz) {
        if(!matchThis(method.getDeclaringClass())){
            return false;
        }
        if(!matchThis(clazz)){
            return false;
        }
        if(exclude(clazz.getName())){
            return false;
        }
        String key = getKey(method,clazz);
        CacheInvokeConfig cacheInvokeConfig = cacheConfigMap.getByMethodInfo(key);
        if(cacheInvokeConfig == CacheInvokeConfig.getNoCacheInvokeConfigInstance()){
            return false;
        } else if (cacheInvokeConfig != null) {
            return true;
        } else {
            cacheInvokeConfig = new CacheInvokeConfig();
            CacheParseUtil.parse(cacheInvokeConfig,method);


            parseTargetClass(cacheInvokeConfig,clazz,method.getName(),method.getParameterTypes());

            if(cacheInvokeConfig.getCachedAnnoConfig() == null && cacheInvokeConfig.getCacheUpdateAnnoConfig() == null
                && cacheInvokeConfig.getCacheInvalidateAnnoConfig() == null){
                cacheConfigMap.putByMethodInfo(key,CacheInvokeConfig.getNoCacheInvokeConfigInstance());
                return false;
            } else {
                cacheConfigMap.putByMethodInfo(key,cacheInvokeConfig);
                return true;
            }
        }
    }

    private void parseTargetClass(CacheInvokeConfig cacheInvokeConfig,Class clazz,String methodName,Class<?>[] parameterTypes){
        if(!clazz.isInterface() && clazz.getSuperclass() != null) {
            parseTargetClass(cacheInvokeConfig,clazz.getSuperclass(),methodName,parameterTypes);
        }
        for(Class ifs : clazz.getInterfaces()) {
            parseTargetClass(cacheInvokeConfig,ifs,methodName,parameterTypes);
        }
        boolean matchThis = matchThis(clazz);
        if (!matchThis) {
            return;
        }
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if(matchMethod(m,methodName,parameterTypes)){
                CacheParseUtil.parse(cacheInvokeConfig,m);
                break;
            }
        }
    }

    private boolean matchMethod(Method method,String methodName,Class<?>[] parameterTypes){
        if(!Modifier.isPublic(method.getModifiers())){
            return false;
        }
        if(!method.getName().equals(methodName)){
            return false;
        }
        for(int i=0;i<parameterTypes.length;i++){
            if (!parameterTypes[i].getName().equals(method.getParameterTypes()[i].getName())){
                return false;
            }
        }
        return true;
    }

    private boolean matchThis(Class clazz){
        if(exclude(clazz.getName())){
           return false;
        }
        return include(clazz.getName());
    }
    private boolean exclude(String name){
        if (name.startsWith("java.")){
            return true;
        }
        if (name.startsWith("org.springframework")){
            return true;
        }
        if(name.contains("$$EnhancerBySpringCGLIB$$")){
            return true;
        }
        return false;
    }

    private boolean include(String name) {
        for(String basePackage : basePackages) {
            if(name.startsWith(basePackage)){
                return true;
            }
        }
        return false;
    }

    public static final String getKey(Method method,Class<?> targetClass){
        StringBuilder key = new StringBuilder();
        key.append(method.getDeclaringClass().getName()).append(".");
        key.append(method.getName()).append(Type.getMethodDescriptor(method));
        if (targetClass != null) {
            key.append("_").append(targetClass.getName());
        }
        return key.toString();
    }
}
