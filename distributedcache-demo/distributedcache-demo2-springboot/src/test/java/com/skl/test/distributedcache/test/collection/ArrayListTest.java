package com.skl.test.distributedcache.test.collection;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ArrayListTest {
    @Test
    public void toList(){
        String[] array={"xx"};
        List<String> list = Arrays.asList(array);
        System.out.println(list);
    }
}
