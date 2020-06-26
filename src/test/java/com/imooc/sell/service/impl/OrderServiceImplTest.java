package com.imooc.sell.service.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XuMingyue
 * @create 2020-06-21 0:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;
    private final String BUYER_OPENID = "1110110";
//    private final String ORDER_ID = "123456";
    private final String ORDER_ID = "1592672792637445358";

    @Test
    void create() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName("燎师兄");
        orderDto.setBuyerAddress("慕课网");
        orderDto.setBuyerPhone("1517265854");
        orderDto.setBuyerOpenid(BUYER_OPENID);
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(1);
        orderDetailList.add(orderDetail);

        orderDto.setOrderDetailList(orderDetailList);
        OrderDto result = orderService.create(orderDto);
        log.info("【创建订单】result={}", result);
    }

    @Test
    void findByOrderId() {
        OrderDto result = orderService.findByOrderId(ORDER_ID);
        log.info("【查询单个订单】 result={}", result);
        Assert.assertEquals(ORDER_ID, result.getOrderId());
    }

    @Test
    void findList() {
        // new PageRequest（a，b）过时
        PageRequest request = PageRequest.of(0,2);

        Page<OrderDto> orderDtoPage = orderService.findList(BUYER_OPENID, request);
        Assert.assertNotEquals(0,orderDtoPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDto orderDto = orderService.findByOrderId(ORDER_ID);
        OrderDto result = orderService.cancel(orderDto);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDto orderDto = orderService.findByOrderId(ORDER_ID);
        OrderDto result = orderService.finish(orderDto);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }


    @Test
    void paid() {
        OrderDto orderDto = orderService.findByOrderId(ORDER_ID);
        OrderDto result = orderService.paid(orderDto);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void list(){
        PageRequest request =PageRequest.of(0, 2);
        Page<OrderDto> orderDtoPage = orderService.findList(request);
//        Assert.assertNotEquals(0,orderDtoPage.getTotalElements());
        Assert.assertTrue("查询所有订单列表",orderDtoPage.getTotalElements()>0);
    }

}