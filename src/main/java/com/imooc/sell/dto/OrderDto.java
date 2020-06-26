package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.utils.EnumUtil;
import com.imooc.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-20 22:17
 * 6-3-a.1  :数据传输对象
 * 与OrderMaster里属性相似
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {

    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    /**
     * //订单总金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单状态【枚举】,默认为新下单
     */
    private  Integer orderStatus;
    /**
     * 支付状态，默认未支付
     */
    private Integer payStatus;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    /**
     * 用于存储一个订单里面的多个商品详情
     */
    List<OrderDetail> orderDetailList;

    /**
     * 用于后端前台的状态判断9-3
     *  @JsonIgnore  //对象转成json时，忽略getOderStatusEnum方法
     * @return
     */
    @JsonIgnore
    public OrderStatusEnum getOderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }
}
