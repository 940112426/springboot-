package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

/**
 * @author XuMingyue
 * @create 2020-06-22 15:40
 */
@Service
@Slf4j
public class BuyerServieImpl implements BuyerService {
    @Autowired
    private OrderService orderService;
    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        OrderDto orderDto = checkOrderOwner(openid, orderId);
        return orderDto;
    }

    @Override
    public OrderDto cancelOrder(String openid, String orderId) {
        OrderDto orderDto = checkOrderOwner(openid, orderId);
        if(orderDto==null){
            log.error("【取消订单】查不到该订单，orerId={},",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_XIST);
        }
        OrderDto cancel = orderService.cancel(orderDto);
        return cancel;
    }

    private OrderDto checkOrderOwner(String openid, String orderId){
        OrderDto orderDto = orderService.findByOrderId(orderId);
        if(orderDto==null){
            return null;
        }
        //判断是否是自己订单
        if(!orderDto.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("【查询订单】 订单的openid不一致，openid={},orderId={}",openid,orderId);
            throw  new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
