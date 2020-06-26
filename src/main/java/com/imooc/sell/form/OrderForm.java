package com.imooc.sell.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author XuMingyue
 * @create 2020-06-22 9:23
 * 6-10-a.1表单验证
 */
@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotEmpty(message="姓名必填")
    private  String name;
    /**
     * 买家手机号
     */
    @NotEmpty(message="地址必填")
    private String phone;

    /**
     * 买家微信openid
     */
    @NotEmpty(message="openid必填")
    private String openid;

    @NotEmpty(message = "地址必填")
    private  String address;

    /**
     * 购物车
     */
    @NotEmpty(message="购物车不能为空")
    private  String items;
}
