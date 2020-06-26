package com.imooc.sell.enums;

import lombok.Getter;

/**
 * @author XuMingyue
 * @create 2020-06-20 16:30
 * 6-1-a.2 支付状态枚举
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
    /**
     *
     */
    WAIL(0,"未支付"),
    /**
     *
     */
    SUCCESS(1,"支付成功");
    private Integer code;
    private String message;
    PayStatusEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
