package com.imooc.sell.enums;

import com.imooc.sell.utils.EnumUtil;
import lombok.Getter;

/**
 * @author XuMingyue
 * @create 2020-06-19 15:40
 * 5-1-e.1  service实现商品状态枚举
 */
@Getter
public enum  ProductStatusEnum implements CodeEnum {
    /**
     *
     */
    UP(0,"上架"),
    /**
     *
     */
    DOWN(1,"下架");

    private Integer code;
    private String message;
    ProductStatusEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
