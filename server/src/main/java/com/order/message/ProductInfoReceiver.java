package com.order.message;

import com.fasterxml.jackson.core.type.TypeReference;
import com.order.DTO.ProductInfoOutput;
import com.order.Utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ProductInfoReceiver {

    //redis模板
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String PRODUCT_STOCK_TEMPLATE = "product_stock_%s";

    /***
     * 接收product服务发送来的扣库存消息
     */
    @RabbitListener(queuesToDeclare = @Queue("productInfo"))
    public void process(String message){
        //接收mq消息
       List<ProductInfoOutput> productInfoOutputs = (List<ProductInfoOutput>) JsonUtil.fromJson(message
               , new TypeReference<List<ProductInfoOutput>>() {});
        log.info("Order MqReceiver 接收到：{}",productInfoOutputs);

        //存储到redis
        for (ProductInfoOutput productInfoOutput : productInfoOutputs) {
            stringRedisTemplate.opsForValue().
                    set(String.format(PRODUCT_STOCK_TEMPLATE,productInfoOutput.getProductId())
                            ,String.valueOf(productInfoOutput.getProductStock()));
        }
    }
}
