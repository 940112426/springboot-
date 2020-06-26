package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-20 16:49
 * 6-1-d 订单详情dao
 */
public interface OrderDetailRespository extends JpaRepository<OrderDetail,String> {
    /**
     * 根据订单id查找订单详情
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
