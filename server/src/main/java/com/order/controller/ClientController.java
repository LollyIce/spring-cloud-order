package com.order.controller;

import com.order.DTO.CartDTO;
import com.order.Entity.ProductInfo;
import com.order.client.ProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
public class ClientController {
    /***
     * 本类注释：
     *      应用间通信
     *     重点: 第四种方法 使用Feign进行应用间通信
     * @return  String
     */
    //第一种方式 使用RestTemplate
    // 缺点：url为固定 若启动多个应用 则只能访问固定的一个不能负载均衡
    @GetMapping("/getProductMsg")
    public String getProductMsg(){
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("http://localhost:8080/msg", String.class);
        log.info("forObject={}",forObject);
        return forObject;
    }

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    //第二种方式 使用LoadBalancerClient 拼接出url 再使用RestTemplate与其他应用通信
    @GetMapping("/getProductMsg2")
    public String getProductMsg2(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("PRODUCT");//此处填写与之通信的模块在Eureka上的名称
        String url = String.format("http://%s:%s", serviceInstance.getHost(), serviceInstance.getPort())+"/msg";
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        log.info("forObject={}",forObject);
        return forObject;
    }

    @Autowired
    private RestTemplate restTemplate ;

    //第三种方式 在配置类中 配置RestTemplate 并加上@LoadBalanced注解 则可在url中直接填写名称进行通信
    @GetMapping("/getProductMsg3")
    public String getProductMsg3(){
        String forObject = restTemplate.getForObject("http://PRODUCT/msg", String.class);
        log.info(forObject);
        return forObject;
    }

    /***
     * 第四种方式 使用Feign进行应用间通信（重点）
     * 引入Feign的坐标
     * 在启动类上加@EnableFeignClients注解
     * 定义接口 本例为 client包下的ProductClient类
     */

    @Autowired
    private ProductClient productClient;

    @GetMapping("/getProductMsg4")
    public String getProductMsg4(){
        String s = productClient.productMsg();
        log.info("s={}",s);
        return s;
    }
    @GetMapping("/getProductList")
    public String getProductList(){
        List<ProductInfo> productInfos = productClient.listForOrder(Arrays.asList("198h9xhn91b","19qjx09h83b"));
        log.info("response={}",productInfos);
        return "ok";
    }


    @GetMapping("/productDecreaseStock")
    public String productDecreaseStock(){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setProductId("19qjx09h83b");
        cartDTO.setProductQuantity(5);
        productClient.decreaseStock(Arrays.asList(cartDTO));
        return "ok";
    }
}
