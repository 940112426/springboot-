package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.SellerInfo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XuMingyue
 * @create 2020-06-24 10:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerService;
    private  static  final  String OPENID="abc";
    @Test
    void findSellerInfoByOpenid() {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(OPENID);
        Assert.assertEquals(OPENID,sellerInfo.getOpenid());
    }
}