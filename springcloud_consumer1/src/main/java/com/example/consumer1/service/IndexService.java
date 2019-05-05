package com.example.consumer1.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @description: 一句话描述功能;
 * @author: zxb
 * @date: 2019/5/3 20:48
 * @comment: 备注
 * @version: V1.0
 */
@Service
public class IndexService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "test1Error")
    public String test1(String name){
        String s = restTemplate.getForObject("http://producer1/hi?name=" + name,String.class);
        return s;
    }

    public String test1Error(String name){

        return "断路器方法";
    }

}
