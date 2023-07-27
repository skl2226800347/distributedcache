package com.skl.distributedcache.starter.springboot;

import com.alibaba.fastjson.JSONObject;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfigTree {
    private ConfigurableEnvironment environment;
    private String prefix;
    public ConfigTree(ConfigurableEnvironment environment,String prefix){
        this.environment = environment;
        this.prefix = prefix;
    }

    public Map<String,Object> getProperties(){
        if(environment.getPropertySources() == null){
            return null;
        }
        Map<String,Object> properties = new HashMap<>();
        for(PropertySource propertySource:environment.getPropertySources()){
            if(propertySource instanceof EnumerablePropertySource){
                for(String propertyName :((EnumerablePropertySource) propertySource).getPropertyNames()){
                    if(propertyName != null && propertyName.startsWith(this.prefix)){
                        String key = propertyName.substring(this.prefix.length());
                        properties.put(key,propertySource.getProperty(propertyName));
                    }
                }
            }
        }
        return properties;
    }
    public ConfigTree subTree(String prefix){
        ConfigTree subTree = new ConfigTree(this.environment,fullOrKeyPrefix(prefix));
        return subTree;
    }

    public <T> T getProperty(String key){
        String newKey = fullOrKeyPrefix(key);
        return (T)this.environment.getProperty(newKey);
    }
    public <T> T getProperty(String key,T defaultValue){
        if(containsProperty(key)){
            return getProperty(key);
        }else {
            return defaultValue;
        }
    }


    private String fullOrKeyPrefix(String prefix){
        StringBuilder fullOrKeyPrefix = new StringBuilder();
        fullOrKeyPrefix.append(this.prefix).append(prefix);
        return fullOrKeyPrefix.toString();
    }

    public String getPrefix() {
        return prefix;
    }

    public boolean containsProperty(String key){
        String newKey = fullOrKeyPrefix(key);
        return environment.containsProperty(newKey);
    }


    public Set<String> getDirectChildrenKeys(){
        Set<String> childrenKeys=getProperties().keySet().stream().map((s)->s.indexOf('.')>0?s.substring(0,s.indexOf('.')):null).filter(s-> s!= null).collect(Collectors.toSet());
        return childrenKeys;
    }

    public Map<String,Object> getProperties(String prefix){
        Objects.requireNonNull(prefix,"prefix is not null");
        if(environment.getPropertySources() == null){
            return null;
        }
        Map<String,Object> properties = new HashMap<>();
        for(PropertySource propertySource:environment.getPropertySources()){
            if(propertySource instanceof EnumerablePropertySource){
                for(String propertyName :((EnumerablePropertySource) propertySource).getPropertyNames()){
                    if(propertyName != null && propertyName.startsWith(prefix)){
                        String key = propertyName.substring(prefix.length());
                        properties.put(key,propertySource.getProperty(propertyName));
                    }
                }
            }
        }
        return properties;
    }

    public Set<String> getDirectChildrenKeys(String prefix){
        Objects.requireNonNull(prefix,"prefix is not null");
        Set<String> childrenKeys=getProperties(prefix).keySet().stream().map((s)->s.indexOf('.')>0?s.substring(0,s.indexOf('.')):null).filter(s-> s!= null).collect(Collectors.toSet());
        return childrenKeys;
    }
}
