package com.skl.test.distributedcache.core.bytecode;

import javassist.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class WrapperDemo {
    protected static final Map<Class, WrapperDemo> WRAPPER_MAP = new ConcurrentHashMap<>();
    private static final AtomicLong INDEX=new AtomicLong();

    public static final WrapperDemo getWrapper(Class<?> cls){
        return WRAPPER_MAP.computeIfAbsent(cls,x->{
            return createWrapper(cls);
        });
    }

    public WrapperDemo(){

    }

    protected static final WrapperDemo createWrapper(Class cls){
        try {
            ClassPool classPool = ClassPool.getDefault();
            String className = cls.getName();
            CtClass ctClass = classPool.makeClass(cls.getSimpleName() + "$" + INDEX.incrementAndGet());
            ctClass.setSuperclass(classPool.get(WrapperDemo.class.getName()));
            StringBuilder methodName = new StringBuilder();
            methodName.append("public  Object invokeMethod(Object instance,String methodName,Class[] types,Object[] args)throws ").append(InvocationTargetException.class.getName()).append("{");
            methodName.append(className).append(" w;");
            methodName.append("try{ w=(").append(className).append(")$1;");
            methodName.append("}catch(Throwable e){");
            methodName.append("throw new IllegalArgumentException(e);");
            methodName.append("}");

            methodName.append("try{");
            Method[] methods = cls.getMethods();
            for (Method m : methods) {
                String mn = m.getName();
                if (m.getDeclaringClass() == Object.class) {
                    continue;
                }
                int len = m.getParameterTypes().length;
                methodName.append("if(\"").append(mn).append("\".equals($2)  && $3.length ==  ").append(len).append(" ){");
                if (m.getReturnType() == Void.TYPE) {
                    methodName.append(" w.").append(mn).append("(");
                    methodName.append(argus(m.getParameterTypes(), "$4")).append(");");
                    methodName.append("  return null;");
                    methodName.append("   }  ");

                } else {
                    m.getParameterTypes();
                    methodName.append(" return ($w)w").append(".").append(mn).append("(");
                    methodName.append(argus(m.getParameterTypes(), "$4")).append(");");
                    methodName.append("   }  ");
                }
            }
            methodName.append("}catch(Throwable e){");
            methodName.append(" throw new IllegalArgumentException(e);");
            methodName.append("}");
            methodName.append(" throw new IllegalArgumentException(\"").append("not found").append("\"); ");
            methodName.append("}");
            System.out.println(methodName.toString());
            CtMethod ctMethod = CtNewMethod.make(methodName.toString(), ctClass);
            ctClass.addMethod(ctMethod);
            Class<?> wrapperCls = ctClass.toClass();
            return (WrapperDemo) wrapperCls.newInstance();
        }catch (RuntimeException e) {
            throw e;
        }catch (Throwable r){
            throw new RuntimeException(r);
        }finally {

        }
    }

    protected static final String argus(Class<?>[] parameterTypes,String name ){
        if (parameterTypes == null || parameterTypes.length ==0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<parameterTypes.length;i++){
            sb.append("(").append(parameterTypes[i].getName()).append(")").append(name).append("[").append(i).append("],");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        return sb.toString();
    }
    public abstract Object invokeMethod(Object instance,String method,Class[] types, Object[]args)throws NoSuchMethodException, InvocationTargetException;


}
