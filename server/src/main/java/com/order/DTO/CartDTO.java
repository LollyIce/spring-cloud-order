package com.order.DTO;

import lombok.Data;

@Data
public class CartDTO {

    //商品ID
    public String productId;
    //商品数量
    public Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
