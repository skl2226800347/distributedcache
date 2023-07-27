package com.skl.distributedcache.core.utils;

import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    public static final List<Class> toClassList(List<String> classNameList) throws Exception{
        if(CollectionUtil.isEmpty(classNameList)){
            return new ArrayList<>();
        }
        List<Class> classList = new ArrayList<>();
        for(String className : classNameList){
            classList.add(Class.forName(className));
        }
        return classList;
    }
    public static void main(String[]args) throws Exception{

    }
}
