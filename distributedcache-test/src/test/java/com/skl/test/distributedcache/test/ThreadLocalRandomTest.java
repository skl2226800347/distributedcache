package com.skl.test.distributedcache.test;

import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomTest {
    @Test
    public void nextInt(){
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int value = random.nextInt(333);
        System.out.println(value);
        value = random.nextInt(333);
        System.out.println(value);
        value = random.nextInt(333);
        System.out.println(value);
    }
}
