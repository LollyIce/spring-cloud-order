package com.order.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RestController
@DefaultProperties(defaultFallback = "defaultFallBack")
public class HystrixController {

    //服务降级 -----------------------------------------

    //@HystrixCommand(fallbackMethod = "fallback")
    @HystrixCommand(commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000"))  //使用默认降级方法：defaultFallBack
    @GetMapping("/getProductInfoList")
    public String getProductInfoList(){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8081/product/listForOrder", Arrays.asList("157875196366160022"), String.class);
        //throw new RuntimeException("");
    }


    //断路器模式 ------------------------------------------
    @HystrixCommand(commandProperties = {@HystrixProperty(name="circuitBreaker.enabled",value = "true"),  //开启断路器模式
            //
                                        @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),
            //计时器 半开启状态 多少秒后放过一个请求看是否请求成功，若成功 断路器关闭，不成功 则改为开启模式 重新计时
                                        @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            //失败率达到多少是开启断路器 注：此值为百分比
                                        @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60")})
    @GetMapping("/getProductInfoList2")
    public String getProductInfoList2(@RequestParam("number") Integer number){
        if(number%2==0){
            return "success";
        }
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject("http://localhost:8081/product/listForOrder", Arrays.asList("157875196366160022"), String.class);
    }



    public String fallback(){
        return "太拥挤了，请稍后再试！！";
    }

    public String defaultFallBack(){
        return "默认：太拥挤了，请稍后再试！！";
    }
}
