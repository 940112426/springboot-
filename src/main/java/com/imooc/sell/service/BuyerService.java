package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;

/**
 * @author XuMingyue
 * @create 2020-06-22 15:37
 */
public interface BuyerService {
    /**
     * 查询一个订单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDto findOrderOne(String openid,String orderId);

    /**
     * 取消定单
     * @param openid
     * @param orderId
     * @return
     */
    OrderDto cancelOrder(String openid,String orderId);
}
