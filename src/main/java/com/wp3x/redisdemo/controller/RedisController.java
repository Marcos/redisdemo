package com.wp3x.redisdemo.controller;

import com.wp3x.redisdemo.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by marcos.ferreira on 10.08.17.
 */

@RestController
@RequestMapping("redis")
public class RedisController {

    @Autowired
    private RedisService service;

    @RequestMapping(method = RequestMethod.GET, value = "/size")
    public Integer getSize() {
        return service.getSize();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/execute")
    public void execute() throws InterruptedException {
        service.execute();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/populate")
    public void populate() {
        service.populate();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/clear")
    public void clear() {
        service.clear();
    }




}
