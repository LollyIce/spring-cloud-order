package com.order.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/***
 * 发送MQ消息（接受方在messages包下）
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MqSenderTest {

    @Autowired
    private  AmqpTemplate amqpTemplate;

    @Test
    public void send(){
        /***
         * 参数解释：
         * 参数一： Exchange的名字
         * 参数二：分组的key
         * 参数三：消息内容
         */
        amqpTemplate.convertAndSend("animalExchange","dog","now "+new Date());
    }
}
