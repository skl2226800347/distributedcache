package com.skl.test.distributedcache.core.bytecode;

import com.skl.test.distributedcache.core.demo.DemoServiceImpl;
import javassist.*;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WrapperDemoTest {

    @Test
    public void getList()throws Exception{
        DemoServiceImpl demoService= new DemoServiceImpl();
        WrapperDemo wrapper = WrapperDemo.getWrapper(demoService.getClass());
        Class[] types={List.class,String.class};
        List<String> param1 = new ArrayList<>();
        param1.add("再见");
        Object[]args = {param1,"你好"};
        Object value = wrapper.invokeMethod(demoService,"getList",types,args);
        System.out.println(value);
    }


    @Test
    public void get()throws Exception{
        DemoServiceImpl demoService= new DemoServiceImpl();
        WrapperDemo wrapper = WrapperDemo.getWrapper(demoService.getClass());
        Class[] types={String.class};
        Object[]args = {"你好"};
        Object value = wrapper.invokeMethod(demoService,"get",types,args);
        System.out.println(value);
    }



    @Test
    public void get_V2()throws Exception{
        DemoServiceImpl demoService= new DemoServiceImpl();
        WrapperDemo wrapper = WrapperDemo.getWrapper(demoService.getClass());
        Class[] types={String.class,String.class};
        Object[]args = {"你好","还行"};
        Object value = wrapper.invokeMethod(demoService,"get",types,args);
        System.out.println(value);
    }

    @Test
    public void getV2()throws Exception{
        DemoServiceImpl demoService= new DemoServiceImpl();
        WrapperDemo wrapper = WrapperDemo.getWrapper(demoService.getClass());
        Class[] types={String.class};
        Object[]args = {"你好"};
        Object value = wrapper.invokeMethod(demoService,"getV2",types,args);
        System.out.println(value);
    }

    @Test
    public void print()throws Exception{
        DemoServiceImpl demoService= new DemoServiceImpl();
        WrapperDemo wrapper = WrapperDemo.getWrapper(demoService.getClass());
        Class[] types={String.class};
        Object[]args = {"你好"};
        wrapper.invokeMethod(demoService,"print",types,args);
    }

    @Test
    public void createClass() throws Exception{
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = classPool.makeClass("Hello");
        StringBuilder methodName = new StringBuilder();
        methodName.append("public void say(){");
        methodName.append("System.out.println(\"你好吗\");");
        methodName.append("}");
        CtMethod ctMethod= CtNewMethod.make(methodName.toString(),ctClass);
        ctClass.addMethod(ctMethod);
        Class cls = ctClass.toClass();
        Object instance = cls.newInstance();
        Method method = cls.getDeclaredMethod("say",null);
        method.invoke(instance);
    }


    public Object t(){
        String $2="";
        String[]$3=null;
        DemoServiceImpl w= null;
        Object[]$4=null;
        if("get".equals($2)  && $3.length ==  2 ){
            return (Object)w.get((java.lang.String)$4[0],(java.lang.String)$4[1]);
        }
        return null;
    }


}
