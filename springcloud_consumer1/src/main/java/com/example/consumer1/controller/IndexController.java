package com.example.consumer1.controller;

import com.example.consumer1.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 一句话描述功能;
 * @author: zxb
 * @date: 2019/5/3 20:47
 * @comment: 备注
 * @version: V1.0
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @RequestMapping("/test")
    public String test(@RequestParam String name){
        return indexService.test1(name);
    }

}
