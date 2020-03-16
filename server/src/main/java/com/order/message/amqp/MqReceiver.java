package com.order.message.amqp;

import com.rabbitmq.tools.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.integration.support.json.JacksonJsonUtils;
import org.springframework.stereotype.Component;

/***
 * 接受MQ消息
 */
@Slf4j
@Component
public class MqReceiver {


    @RabbitListener(bindings = @QueueBinding(value = @Queue("animalQueue"),
            exchange = @Exchange("animalExchange"),
            key = "cat"))
    public void cat(String message){
        log.info("cat:MqReceiver:{}",message);
    }

    @RabbitListener(bindings = @QueueBinding(value = @Queue("animalQueue"),
            exchange = @Exchange("animalExchange"),
            key = "dog"))
    public void dog(String message){
        log.info("dog:MqReceiver:{}",message);
    }

  /*  @RabbitListener(queues = "myQueue")
    public void process(String com.order.message){
        log.info("myQueue:MqReceiver:{}",com.order.message);
    }*/
}
