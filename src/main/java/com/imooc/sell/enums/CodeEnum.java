package com.imooc.sell.enums;

/**
 * @author XuMingyue
 * @create 2020-06-23 14:31
 * 订单、支付状态判断接口
 */
public interface CodeEnum<T> {
    /**
     * 错误码
     * @return
     */
    Integer getCode();
}
