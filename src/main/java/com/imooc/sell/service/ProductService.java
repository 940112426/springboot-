package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author XuMingyue
 * @create 2020-06-19 15:30
 *  5-1-d  ervice
 *  6-3-b.3 完成库存方法接口
 **/
public interface ProductService {
    /**
     * 根据商品id查找商品
     * @param productId
     * @return
     */
    Optional<ProductInfo> findByProductId(String productId);

    /**
     * 查询所有在架商品
     * @return
     */
    List<ProductInfo > findUpAll();

    /**
     * 分页查询所有商品
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品信息
     * @param productInfo
     * @return
     */
    ProductInfo save(ProductInfo productInfo);

    /**
     * 加库存
     * @param cartDtoList
     */
    void increateStock(List<CartDto> cartDtoList);

    /**
     * 减库存
     * @param cartDtoList
     */
    void decreaseStock(List<CartDto> cartDtoList);

    /**
     * 上架商品
     * @param productId
     * @return
     */
    ProductInfo onSale(String productId);

    /**
     * 下架商品
     * @param productId
     * @return
     */
    ProductInfo offSale(String productId);
}
