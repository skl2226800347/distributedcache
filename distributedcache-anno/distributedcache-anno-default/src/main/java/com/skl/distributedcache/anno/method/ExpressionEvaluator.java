package com.skl.distributedcache.anno.method;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.function.Function;

public class ExpressionEvaluator implements Function<Object,Object> {
    private Function<Object,Object> target;
    public ExpressionEvaluator(String script, Method defineMethod){
        Object[] el = parseEl(script);
        if(el[1] == EL.SPRING_EL) {
            target = new SpelEvaluator(script,defineMethod);
        } else {
            target = null;
        }
    }

    @Override
    public Object apply(Object o) {
        return target.apply(o);
    }

    private Object[] parseEl(String scritp){
        Object[] el= new Object[2];
        el[0] = scritp;
        el[1] = EL.SPRING_EL;
        return el;
    }
}
class SpelEvaluator implements Function<Object,Object>{
    private static ExpressionParser expressionParser;
    private static ParameterNameDiscoverer parameterNameDiscoverer;
    static {
        try {
            Class modeClass = Class.forName("org.springframework.expression.spel.SpelCompilerMode");
            try {
                Constructor<SpelParserConfiguration> constructor = SpelParserConfiguration.class.getConstructor(modeClass, ClassLoader.class);
                Object mode = modeClass.getField("IMMEDIATE").get(null);
                SpelParserConfiguration config = constructor.newInstance(mode, SpelEvaluator.class.getClassLoader());
                expressionParser = new SpelExpressionParser(config);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (ClassNotFoundException e) {
            expressionParser = new SpelExpressionParser();
        }
        parameterNameDiscoverer = new DefaultParameterNameDiscoverer();
    }

    private Expression expression;
    private String[] parameterNames;

    public SpelEvaluator(String script,Method defineMethod){
        expression = expressionParser.parseExpression(script);
        if(defineMethod.getParameterCount() >0){
            parameterNames = parameterNameDiscoverer.getParameterNames(defineMethod);
        }
    }

    @Override
    public Object apply(Object o) {
        EvaluationContext evaluationContext = new StandardEvaluationContext(o);
        CacheInvokeContext cic =(CacheInvokeContext)o;
        for(int i=0;i<parameterNames.length;i++){
            evaluationContext.setVariable(parameterNames[i],cic.getArgs()[i]);
        }
        evaluationContext.setVariable("result",cic.getResult());
        return expression.getValue(evaluationContext);
    }
}
