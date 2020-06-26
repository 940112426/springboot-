package com.imooc.sell.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author XuMingyue
 * @create 2020-06-24 9:51
 * 卖家信息dao
 */
@Data
@Entity
public class SellerInfo {
    @Id
    private  String sellerId;
    private String username;
    private  String password;
    private  String openid;
//    private Date createTime;
//    private Date updateTime;

}
