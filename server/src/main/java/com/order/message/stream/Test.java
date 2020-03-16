package com.order.message.stream;

import com.order.Entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder; //注意MessageBuilder的导包不能错
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * spring-cloud-stream 发送消息测试类
 */
@RestController
@RequestMapping("/test")
public class Test {
    @Autowired
    private StreamClient streamClient;
    @GetMapping("/login")
    public void send(){
        //注意MessageBuilder的导包不能错
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("axax");
        streamClient.orderStreamOutput().send(MessageBuilder.withPayload(orderDetail).build());
    }
}
