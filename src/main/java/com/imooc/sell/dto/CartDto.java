package com.imooc.sell.dto;

import lombok.Data;

/**
 * @author XuMingyue
 * @create 2020-06-20 23:49
 * 6-3-b.3。1  购物车两个字段：商品id和购买的数量
 */
@Data
public class CartDto {
   private  String productId;
   private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
