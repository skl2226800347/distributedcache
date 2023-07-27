package com.skl.distributedcache.anno.method;

import java.util.function.Function;

public class CacheUpdateAnnoConfig extends CacheAnnoConfig{
    private String value;
    private Function<CacheInvokeContext,Object> valueEvaluator;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Function<CacheInvokeContext, Object> getValueEvaluator() {
        return valueEvaluator;
    }

    public void setValueEvaluator(Function<CacheInvokeContext, Object> valueEvaluator) {
        this.valueEvaluator = valueEvaluator;
    }
}
