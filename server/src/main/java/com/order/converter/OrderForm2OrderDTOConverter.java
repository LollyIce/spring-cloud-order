package com.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.order.DTO.OrderDTO;
import com.order.Entity.OrderDetail;
import com.order.form.OrderForm;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            System.out.println("json转换错误");
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
