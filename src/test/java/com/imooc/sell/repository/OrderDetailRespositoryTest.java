package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-20 17:56
 *  6-1-f 订单详情表dao测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class OrderDetailRespositoryTest {

    @Autowired
    private OrderDetailRespository respository;
    @Test
    void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456789");
        orderDetail.setOrderId("11255211");
        orderDetail.setProductIcon("http://xsdd.jpg");
        orderDetail.setProductId("1425252");
        orderDetail.setProductName("C柠檬");
        orderDetail.setProductPrice(new BigDecimal(5.0));
        orderDetail.setProductQuantity(40);
        OrderDetail result = respository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    void findByOrOrderId() {
        List<OrderDetail> orderDetailList = respository.findByOrderId("11255211");
        Assert.assertNotEquals(0,orderDetailList.size());

    }
}