package com.imooc.sell.enums;

import lombok.Getter;

/**
 * @author XuMingyue
 * @create 2020-06-20 22:36
 * 6-3-b.1.1 :异常返回给前段的提示消息
 */
@Getter
public enum ResultEnum {
    /**
     *
     */
    SUCCESS(0,"取消订单成功"),

    /*6-10表单验证*/
    PARAM_ERROR(1,"表单参数不正确"),


    PRODUCT_NOT_EXITS(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),

    ORDER_NOT_XIST(12,"订单不存在"),
    ORDERDETAIL_NOT_XIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
     ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付状态不正确"),
    /*6-10*/
    CART_EMPTY(18,"购物车不能为空"),

    ORDER_OWNER_ERROR(19,"该订单不属于该用户"),

    ORDER_CANCEL_SUCCESS(20,"订单取消成功"),
    ORDER_FINISH_SUCCESS(21,"订单完结成功"),

    PRODUCT_ONSALE(22,"商品已在架"),
    PRODUCT_OFFSALE(23,"商品已下架"),
    LOGIN_FAIL(24,"登录失败，登录信息不正确"),
    LOGOUT_SUCCESS(25,"登出成功")
        ;
    private  Integer code;
    private  String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
