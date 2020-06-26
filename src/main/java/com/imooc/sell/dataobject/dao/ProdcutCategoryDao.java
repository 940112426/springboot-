package com.imooc.sell.dataobject.dao;

import com.imooc.sell.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author XuMingyue
 * @create 2020-06-24 19:32
 * dao层
 */
public class ProdcutCategoryDao {
    @Autowired
    private ProductCategoryMapper mapper;
    public int insertByMap(Map<String,Object> map){
        return mapper.insertByMap(map);
    }
}
