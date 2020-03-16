package com.order.controller;


import com.order.DTO.OrderDTO;
import com.order.Exception.OrderException;
import com.order.ResultVO.ResultVO;
import com.order.Utils.ResultVOUtil;
import com.order.converter.OrderForm2OrderDTOConverter;
import com.order.enums.ResultEnum;
import com.order.form.OrderForm;
import com.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
            if(bindingResult.hasErrors()){
                log.error("[创建订单] 参数不正确,orderForm={}",orderForm);
                throw new OrderException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
            }
            //orderForm 转为 orderDTO
            OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
            if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
                log.error("[创建订单] 购物车信息为空");
                throw new OrderException(ResultEnum.CART_EMPTY);
            }

        OrderDTO result = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVOUtil.success(map);
    }

    /***
     * 完结订单
     * @param orderId
     * @return
     */
    @PostMapping("/finish")
    public ResultVO<OrderDTO> finish(@RequestParam("orderId") String orderId){
        return ResultVOUtil.success(orderService.finish(orderId));
    }

}
