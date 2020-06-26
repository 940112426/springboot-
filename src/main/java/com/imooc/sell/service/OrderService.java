package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author XuMingyue
 * @create 2020-06-20 22:14
 * 6-3-a.0  订单接口
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderDto
     * @return
     */
    OrderDto create(OrderDto orderDto);

    /**
     * 查询单个订单,放在dao中
     * @param orderId
     * @return
     */
    OrderDto findByOrderId(String orderId);

    /**
     * 查询订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);

    /**
     * 取消定单
     * @param orderDto
     * @return
     */
    OrderDto cancel(OrderDto orderDto);

    /**
     * 完结订单
     * @param orderDto
     * @return
     */
    OrderDto finish(OrderDto orderDto);

    /**
     * 支付订单
     * @param orderDto
     * @return
     */
    OrderDto paid(OrderDto orderDto);


    /**
     * 卖家端
     * @param pageable
     * @return
     */
    Page<OrderDto> findList(Pageable pageable);
}
