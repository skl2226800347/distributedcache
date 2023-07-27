package com.skl.distributedcache.core.bytecode;

import javassist.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class Proxy {
    public static final Map<Class, Proxy> PROXY_MAP = new ConcurrentHashMap<>();
    public static final AtomicLong INDEX = new AtomicLong();
    public static final  ClassPool CLASS_POOL = ClassPool.getDefault();
    public static final Proxy getProxy(Class<?>  cls, InvocationHandler invocationHandler){
        return PROXY_MAP.computeIfAbsent(cls,x->{
            return createProxy(cls,invocationHandler);
        });
    }

    public static final Proxy createProxy(Class<?> cls, InvocationHandler invocationHandler){
        try {
            ClassPool classPool = ClassPool.getDefault();
            String className = cls.getName();
            String proxyClassName = Proxy.class.getSimpleName() + INDEX.incrementAndGet();
            CtClass ctClass = classPool.makeClass(proxyClassName);
            ctClass.setSuperclass(classPool.get(Proxy.class.getName()));

            //添加字段
            StringBuilder fieldSource = new StringBuilder();
            fieldSource.append("public  ").append(InvocationHandler.class.getName()).append(" handler;");
            CtField ctField = CtField.make(fieldSource.toString(),ctClass);
            ctClass.addField(ctField);
            //添加构造方法
            StringBuilder constructor = new StringBuilder();
            constructor.append("public ").append(proxyClassName).append("(").append(InvocationHandler.class.getName()).append(" handler){");
            constructor.append(" this.handler =$1;");
            constructor.append("}");
            System.out.println("constructor="+constructor.toString());
            CtConstructor ctConstructor = CtNewConstructor.make(constructor.toString(),ctClass);
            ctClass.addConstructor(ctConstructor);


            StringBuilder constructor2 = new StringBuilder();
            constructor2.append("public ").append(proxyClassName).append("(){");
            constructor2.append("}");
            System.out.println("constructor2="+constructor2.toString());
            CtConstructor ctConstructor2 = CtNewConstructor.make(constructor2.toString(),ctClass);
            ctClass.addConstructor(ctConstructor2);
            //添加方法
            StringBuilder methodName = new StringBuilder();
            methodName.append("public  Object invokeMethod(Object instance,String methodName,Class[] types,Object[] args)throws ").append(InvocationTargetException.class.getName()).append("{");
            methodName.append(className).append(" w;");
            methodName.append("try{ w=(").append(className).append(")$1;");
            methodName.append("}catch(Throwable e){");
            methodName.append("throw new IllegalArgumentException(e);");
            methodName.append("}");

            methodName.append("try{");
            methodName.append(" java.lang.reflect.Method method = w.getClass().getMethod($2,$3) ;");
            Method[] methods = cls.getMethods();
            for (Method m : methods) {
                String mn = m.getName();
                if (m.getDeclaringClass() == Object.class) {
                    continue;
                }
                int len = m.getParameterTypes().length;
                methodName.append("if(\"").append(mn).append("\".equals($2)  && $3.length ==  ").append(len).append(" ){");
                if (m.getReturnType() == Void.TYPE) {
                    methodName.append("  handler.invoke($1,method,$4);");
                    methodName.append("   }  ");

                } else {
                    m.getParameterTypes();
                    methodName.append(" return ($w) handler.invoke($1,method,$4);");
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
            Class<?> proxyCls = ctClass.toClass();
            Proxy plusProxy =  (Proxy) proxyCls.newInstance();

            for (Field f:plusProxy.getClass().getFields()){
                System.out.println(f.getName());
            }
            Field field = plusProxy.getClass().getField("handler");
            field.set(plusProxy,invocationHandler);
            return plusProxy;
        }catch (RuntimeException e) {
            throw e;
        }catch (Throwable r){
            throw new RuntimeException(r);
        }finally {

        }

    }


    public abstract Object invokeMethod(Object instance,String method,Class[] types, Object[]args)throws NoSuchMethodException, InvocationTargetException;

}
