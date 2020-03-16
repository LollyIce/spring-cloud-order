package com.order.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/***
 * spring-cloud-stream 发送接受接口
 */
public interface StreamClient {

    String STREAM_NAME = "orderMessage";
    String STREAM_INPUT = "orderMessageInput";
    String STREAM_OUTPUT = "orderMessageOutput";

    @Output(StreamClient.STREAM_NAME)
    MessageChannel orderStreamOutput();    //此处返回值不能改

    @Input(StreamClient.STREAM_NAME)
    SubscribableChannel orderStreamInput();   //此处返回值不能改


}
