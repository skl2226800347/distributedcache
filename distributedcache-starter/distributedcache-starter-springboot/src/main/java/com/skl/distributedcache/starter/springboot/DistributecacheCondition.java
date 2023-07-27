package com.skl.distributedcache.starter.springboot;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DistributecacheCondition extends SpringBootCondition {

    private String[] cacheTypes;
    public DistributecacheCondition(String ...cacheTypes){
        Objects.requireNonNull(cacheTypes,"cacheTypes is not null");
        Assert.isTrue(cacheTypes.length > 0,"cacheTypes array length greater than 0");
        this.cacheTypes = cacheTypes;
    }
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigTree configTree = new ConfigTree((ConfigurableEnvironment) context.getEnvironment(),"distributecache.");
        if(match(configTree,"local.") || match(configTree,"external.")){
            return ConditionOutcome.match();
        } else {
            return ConditionOutcome.noMatch("no cachTypes match");
        }
    }

    private boolean match(ConfigTree configTree,String prefix){
        Map<String,Object> propertiesMap = configTree.subTree(prefix).getProperties();
        List<String> cacheAreas = propertiesMap.keySet().stream().map((s)->s.substring(0,s.indexOf("."))).collect(Collectors.toList());
        List<String> cacheTypeList = Arrays.asList(cacheTypes);
        boolean isMath = cacheAreas.stream().anyMatch((s)->cacheTypeList.contains(propertiesMap.get(s+".type")));
        return isMath;
    }
}
