package com.imooc.sell.service;

import com.imooc.sell.dataobject.ProductCategory;

import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-19 13:29
 * 4-3-a service接口
 */
public interface CategoryService {

    /**
     *查询商品类目
     * @param categoryId
     * @return
     */
    ProductCategory getOne(Integer categoryId);

    /**
     * 查询所有商品类目
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据类目类型查询商品类目
     * @param categoryTypeList
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 增加商品类目
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);
}
