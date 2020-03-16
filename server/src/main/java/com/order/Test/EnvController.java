package com.order.Test;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * 用于测试统一配置中心是否会自动刷新配置
 */
@RestController
@RequestMapping("/env")
@RefreshScope  // 这个注解声明了刷新配置的范围，如果使用config配置类的话，就声明到配置类上即可
public class EnvController {

    @Value("${env}")
    private String env;

    @GetMapping("/print")
    public String print(){
        return env;
    }

}
