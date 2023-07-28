package com.skl.distributedcache.test;

import com.skl.distributedcache.anno.config.EnableMethodCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMethodCache(basePackages = "com.skl.distributedcache")
public class Demo2Application {
    private static final Logger logger = LoggerFactory.getLogger(Demo2Application.class);
    public static final void main(String[]args) throws Exception{
        SpringApplication.run(Demo2Application.class);
        logger.info("启动成功");
    }
}
