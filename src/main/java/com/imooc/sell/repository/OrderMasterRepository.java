package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XuMingyue
 * @create 2020-06-20 16:41
 * 6-1-c 买家订单Dao
 *
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String> {
    /**
     * 根据订单的买家微信id分页查询
     * @param openid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);

    /**
     * 查询单个订单
     * @param orderId
     * @return
     */
    OrderMaster findByOrderId(String orderId);
}
