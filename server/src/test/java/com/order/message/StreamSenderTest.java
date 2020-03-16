package com.order.message;

import com.order.Entity.OrderDetail;
import com.order.message.stream.StreamClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.support.MessageBuilder; //此包不能导错
import org.springframework.test.context.junit4.SpringRunner;

/***
 * Stream 发送方
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StreamSenderTest {

    @Autowired
    private StreamClient streamClient;

    @Test
    public void send(){
        //注意MessageBuilder的导包不能错
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("axax");
        streamClient.orderStreamOutput().send(MessageBuilder.withPayload(orderDetail).build());
    }
}
