package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

/**
 * @author XuMingyue
 * @create 2020-06-19 14:53
 *  5-1-b  DAO
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,Integer> {
    /**
     * 根据商品状态查询
     * @param productStatus
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /**
     * 根据商品id查找商品
     * @param productId
     * @return
     */
    Optional<ProductInfo> findByProductId(String productId);
}
