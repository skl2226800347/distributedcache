package com.skl.distributedcache.anno.config;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

import java.util.ArrayList;
import java.util.List;

public class ConfigSelector extends AdviceModeImportSelector<EnableMethodCache> {


    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode){
            case PROXY:
                List<String> result = new ArrayList();
                result.add(AutoProxyRegistrar.class.getName());
                result.add(DistributedcacheProxyConfiguration.class.getName());
                return result.toArray(new String[result.size()]);
            case ASPECTJ:
            default:
                return null;
        }
    }
}
