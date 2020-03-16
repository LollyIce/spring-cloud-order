package com.order.client;

import com.order.DTO.CartDTO;
import com.order.Entity.ProductInfo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@FeignClient(name = "product")   //与之通信的应用名称
public interface ProductClient {
    /***
     * 测试用例 调用product服务的方法
     * @return
     */
    @GetMapping("/msg")  //与之通信的方法 与通信的方法名一样
    String productMsg();  //方法名可以与通信的方法名不一样

    /***
     * 获取商品列表（给订单服务用）
     * @param productIdList
     * @return
     */
    @PostMapping("/product/listForOrder")
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList);

    /***
     * 扣库存
     * @param cartDTOList
     */
    @PostMapping("/product/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList);
}
