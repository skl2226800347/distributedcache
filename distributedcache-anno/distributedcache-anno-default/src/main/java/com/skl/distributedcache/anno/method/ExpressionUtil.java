package com.skl.distributedcache.anno.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExpressionUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExpressionUtil.class);
    static final Object EVAL_FAILURE = new Object();
    public static final Object evalKey(CacheAnnoConfig cacheAnnoConfig,CacheInvokeContext cacheInvokeContext){
        try {
            if (cacheAnnoConfig.getKeyEvaluator() == null) {
                ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(cacheAnnoConfig.getKey(), cacheAnnoConfig.getDefineMethod());
                cacheAnnoConfig.setKeyEvaluator((o) -> expressionEvaluator.apply(o));
            }
            return cacheAnnoConfig.getKeyEvaluator().apply(cacheInvokeContext);
        }catch (Exception e){
            logger.error("occur error with eval key script:{} defineMethod:{}",cacheAnnoConfig.getKey(),cacheAnnoConfig.getDefineMethod());
            return null;
        }
    }
    public static final Object evalValue(CacheUpdateAnnoConfig cacheUpdateAnnoConfig,CacheInvokeContext cacheInvokeContext){
        try {
            if (cacheUpdateAnnoConfig.getValueEvaluator() == null) {
                ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator(cacheUpdateAnnoConfig.getValue(), cacheUpdateAnnoConfig.getDefineMethod());
                cacheUpdateAnnoConfig.setValueEvaluator((o) -> expressionEvaluator.apply(o));
            }
            return cacheUpdateAnnoConfig.getValueEvaluator().apply(cacheInvokeContext);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("occur error with eval value script:{} defineMethod:{}",cacheUpdateAnnoConfig.getValue(),cacheUpdateAnnoConfig.getDefineMethod());
            return EVAL_FAILURE;
        }
    }
}
