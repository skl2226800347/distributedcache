package com.skl.test.distributedcache.test;

import com.skl.distributedcache.test.One1Application;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = One1Application.class)
@SpringBootConfiguration
@Rollback(false)
public class AbstractTestCase {

}
