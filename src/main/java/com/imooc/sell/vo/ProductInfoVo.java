package com.imooc.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author XuMingyue
 * @create 2020-06-19 18:14
 * 商品详情
 * 5-3-a.3
 */
@Data
public class ProductInfoVo implements Serializable {
    private static final long serialVersionUID = 1038769094608742952L;
    @JsonProperty("id")
    private String productId;
    @JsonProperty("name")
    private  String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("icon")
    private String productIcon;
}
