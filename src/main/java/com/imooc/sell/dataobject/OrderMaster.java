package com.imooc.sell.dataobject;

import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Data
@DynamicUpdate  /**自动更新updateTIme字段*/
/**
 * @author XuMingyue
 * @create 2020-06-20 16:19
 * 6-1-a.0 买家订单实体
 */
public class OrderMaster {
    @Id
    private String orderId;
    private String buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private BigDecimal orderAmount;
    /**
     *  //订单状态【枚举】,默认为新下单
     */
    private  Integer orderStatus= OrderStatusEnum.NEW.getCode();
    /**
     *   //支付状态，默认未支付
     */
    private Integer payStatus= PayStatusEnum.WAIL.getCode();
    private Date createTime;
    private Date updateTime;


}
