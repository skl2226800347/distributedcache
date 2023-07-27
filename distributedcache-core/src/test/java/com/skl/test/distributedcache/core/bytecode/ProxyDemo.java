package com.skl.test.distributedcache.core.bytecode;

import javassist.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ProxyDemo {
    public static final Map<Class, ProxyDemo> PROXY_MAP = new ConcurrentHashMap<>();
    public static final AtomicLong INDEX = new AtomicLong();
    public static final  ClassPool CLASS_POOL = ClassPool.getDefault();
    public static final ProxyDemo getProxy(Class<?>  cls){
        return PROXY_MAP.computeIfAbsent(cls,x->{
            return createProxy(cls);
        });
    }

    public static final ProxyDemo createProxy(Class<?> cls){
        try {
            String proxyName = ProxyDemo.class.getSimpleName() + INDEX.incrementAndGet();
            Method[] ms = cls.getMethods();
            CtClass ctClass = CLASS_POOL.makeClass(proxyName);
            ctClass.setSuperclass(CLASS_POOL.get(ProxyDemo.class.getName()));
            CtClass[] interfaces = new CtClass[1];
            interfaces[0]= CLASS_POOL.get(cls.getName());
            ctClass.setInterfaces(interfaces);
            //生成字段
            StringBuilder field1Source = new StringBuilder();
            field1Source.append("public static java.lang.reflect.Method[] methods;");
            StringBuilder field2Source = new StringBuilder();
            field2Source.append("private ").append(InvocationHandler.class.getName()).append(" handler;");
            CtField ctField1 = CtField.make(field1Source.toString(),ctClass);
            CtField ctField2 = CtField.make(field2Source.toString(),ctClass);
            System.out.println("field1Source:"+field1Source);
            System.out.println("field2Source:"+field2Source);
            ctClass.addField(ctField1);
            ctClass.addField(ctField2);
            //添加构造方法
            StringBuilder constructMethodSource = new StringBuilder();
            constructMethodSource.append("public ").append(proxyName).append("(").append(InvocationHandler.class.getName());
            constructMethodSource.append(" handler)").append("{");
            constructMethodSource.append("this.handler = $1 ;");
            constructMethodSource.append("}");
            CtConstructor ctConstructor = CtNewConstructor.make(constructMethodSource.toString(),ctClass);
            ctClass.addConstructor(ctConstructor);

            StringBuilder construct2MethodSource = new StringBuilder();
            construct2MethodSource.append("public ").append(proxyName).append("(){}");
            CtConstructor ctConstructor2 = CtNewConstructor.make(construct2MethodSource.toString(),ctClass);
            ctClass.addConstructor(ctConstructor2);

            System.out.println("constructMethodSource:"+constructMethodSource);
            List<CtMethod> ctMethods = new ArrayList<>();
            List<Method> methods = new ArrayList<>();
            for (int i = 0; i < ms.length; i++) {
                StringBuilder methodSource = new StringBuilder();
                Method m = ms[i];
                if (m.getDeclaringClass() == Object.class) {
                    continue;
                }
                Class[] pts = m.getParameterTypes();
                int len = pts.length;
                methodSource.append("public ").append(m.getReturnType().getName()).append("  ").append(m.getName());
                methodSource.append("(").append(args(pts)).append("){ ");
                methodSource.append("Object[] args = new Object[").append(len).append("]; ");
                for (int j = 0; j < pts.length; j++) {
                    methodSource.append("args[").append(j).append("]=").append("($w)").append("$").append(j).append(";");
                }
                if(m.getReturnType()==Void.TYPE){
                    methodSource.append(" handler.invoke(this,methods[").append(i).append("],args);");
                    methodSource.append("return  null; ");
                } else {
                    methodSource.append("Object ret = handler.invoke(this,methods[").append(i).append("],args);");
                    methodSource.append("return (").append(m.getReturnType().getName()).append(")ret ; ");
                }
                methodSource.append("}");
                //System.out.println("methodSource:"+methodSource);
                CtMethod ctMethod = CtNewMethod.make(methodSource.toString(), ctClass);
                ctMethods.add(ctMethod);
                ctClass.addMethod(ctMethod);
                methods.add(m);
            }
            StringBuilder methodSource2 = new StringBuilder();
            methodSource2.append("public Object newInstance(").append(InvocationHandler.class.getName()).append(" handler){");
            methodSource2.append("return new ").append(proxyName).append("($1);");
            methodSource2.append("}");
            System.out.println("methodSourc2:"+methodSource2);
            CtMethod ctMethod2 = CtNewMethod.make(methodSource2.toString(),ctClass);
            ctClass.addMethod(ctMethod2);
            Class proxyClass = ctClass.toClass();
            proxyClass.getField("methods").set(null,methods.toArray(new Method[0]));
            return (ProxyDemo)proxyClass.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
    protected static final String args(Class[] pts){
        StringBuilder aguments = new StringBuilder();
        for (int i=0;i<pts.length;i++){
            aguments.append(pts[i].getName()).append(" arg").append(i).append(",");
        }
        aguments.deleteCharAt(aguments.lastIndexOf(","));
        return aguments.toString();
    }

    abstract public Object newInstance(InvocationHandler invocationHandler);
}
