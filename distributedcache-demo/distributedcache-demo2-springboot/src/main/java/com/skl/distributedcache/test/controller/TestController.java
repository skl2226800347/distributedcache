package com.skl.distributedcache.test.controller;

import com.skl.distributedcache.test.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    DemoService demoService;
    @ResponseBody
    @RequestMapping("/get")
    public Object get(String id){
        return demoService.get(id);
    }

    @ResponseBody
    @RequestMapping("/update")
    public void update(String id,String value){
         demoService.update(id,value);
    }

    @ResponseBody
    @RequestMapping("/remove")
    public void remove(String id){
        demoService.remove(id);
    }
}
