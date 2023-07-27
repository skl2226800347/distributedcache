package com.skl.distributedcache.starter.springboot.remoting;

import com.skl.distributedcache.starter.springboot.ConfigTree;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DistributecacheRemotingCondition extends SpringBootCondition {

    private String[] remotingTypes;
    public DistributecacheRemotingCondition(String ... remotingTypes){
        this.remotingTypes = remotingTypes;
    }
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConfigTree configTree = new ConfigTree((ConfigurableEnvironment)context.getEnvironment(),"distributecache.");
        if(match(configTree,"remoting.")){
            return ConditionOutcome.match();
        } else {
            return ConditionOutcome.noMatch("remoting type is not match");
        }
    }
    private boolean match(ConfigTree configTree,String prefix){
        ConfigTree subTree  = configTree.subTree(prefix);
        Map<String,Object> properties = subTree.getProperties();
        Object remotingType = properties.get("type");
        List<String> remotingTypeList = Arrays.asList(this.remotingTypes);
        boolean isMatch =remotingTypeList.contains(remotingType);
        return isMatch;
    }

}
