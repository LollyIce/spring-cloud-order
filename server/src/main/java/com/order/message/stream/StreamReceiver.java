package com.order.message.stream;

import com.order.Entity.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/***
 * Stream接收消息方
 */
@Component
@EnableBinding(StreamClient.class)  //参数为Stream的接口
@Slf4j
public class StreamReceiver {

        @StreamListener(value = StreamClient.STREAM_NAME)
        public void process(Object message){
            log.info("StreamReceiver : {}",message);
        }
}
