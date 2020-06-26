package com.imooc.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-19 18:10
 * 5-3-a.2
 * 商品包类目
 *
 */
@Data
public class ProductVo implements Serializable {

    private static final long serialVersionUID = 3594636979665526325L;
    /**
     * 序列化给前端，将其名字categoryName该为name
     */
    @JsonProperty("name")
    private  String categoryName;
    @JsonProperty("type")
    private  Integer categeoryType;
    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVos;
}
