package com.imooc.sell.repository;

import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XuMingyue
 * @create 2020-06-24 9:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class SellerInfoRepositoryTest {
    @Autowired
    private  SellerInfoRepository repository;

    //新增
    @Test
    public void save(){

        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);
    }

    @Test
    void findByOpenid() {
        SellerInfo result = repository.findByOpenid("abc");
        Assert.assertEquals("abc",result.getOpenid());
    }
}