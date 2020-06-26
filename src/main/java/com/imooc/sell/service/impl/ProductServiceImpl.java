package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.ProductService;
import org.hibernate.annotations.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Key;
import java.util.List;
import java.util.Optional;

/**
 * @author XuMingyue
 * @create 2020-06-19 15:35
 *  5-1-e.0  service实现
 */
@Transactional
@Service
@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductInfoRepository repository;
//    @Override
//    public Optional<ProductInfo> findByProductId(String productId) {
//        return repository.findByProductId(productId);
//    }

//    @Override
//    public Optional<ProductInfo> findById(String productId) {
//        return repository.findById(Integer.parseInt(productId));
//    }

    @Override
    /**
     * @Cacheable(key="123")
     */
    public Optional<ProductInfo> findByProductId(String productId) {
        return repository.findByProductId(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        //在架0状态，通过枚举解决
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    /**
     * @CachePut(key = "123")
     */
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increateStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            Optional<ProductInfo> productInfo = repository.findByProductId(cartDto.getProductId());
            if(productInfo==null || !productInfo.isPresent()){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXITS);
            }
            Integer result = productInfo.get().getProductStock() + cartDto.getProductQuantity();
            productInfo.get().setProductStock(result);
            repository.save(productInfo.get());
        }

    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            //根据商品id查找商品信息
            Optional<ProductInfo> productInfo = repository.findByProductId(cartDto.getProductId());
            if(productInfo==null || !productInfo.isPresent()){
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXITS);
            }
            //得到库存剩余容量
            Integer result = productInfo.get().getProductStock() - cartDto.getProductQuantity();
            if(result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.get().setProductStock(result);
            repository.save(productInfo.get());

        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        Optional<ProductInfo> productInfo = repository.findByProductId(productId);
        if(productInfo==null || !productInfo.isPresent()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXITS);
        }
        if(productInfo.get().getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_ONSALE);
        }
        productInfo.get().setProductStatus(ProductStatusEnum.UP.getCode());

        return repository.save(productInfo.get());
    }

    @Override
    public ProductInfo offSale(String productId) {
        Optional<ProductInfo> productInfo = repository.findByProductId(productId);
        if(productInfo==null || !productInfo.isPresent()){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXITS);
        }
        if(productInfo.get().getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_OFFSALE);
        }
        productInfo.get().setProductStatus(ProductStatusEnum.DOWN.getCode());

        return repository.save(productInfo.get());
    }
}
