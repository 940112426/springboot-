package com.imooc.sell.dataobject.mapper;

import com.imooc.sell.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author XuMingyue
 * @create 2020-06-24 17:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;
    @Test
    void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name","京东618");
        map.put("category_type",101);
        int result = mapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }
    @Test
    void insertByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("天猫双11");
        productCategory.setCategoryType(11);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }
    @Test
    void findByCategoryType(){
        ProductCategory productCategory = mapper.findByCategoryType(11);
        Assert.assertNotNull(productCategory);
    }
    @Test
    void findByCategoryName(){
        List<ProductCategory> categoryList = mapper.findByCategoryName("女生最爱");
        Assert.assertNotEquals(0,categoryList.size());
    }
    @Test
    void updateByCategoryType(){
        int result = mapper.updateByCategoryType("淘宝双12",101);
        Assert.assertNotNull(result);
    }
    @Test
    void deleteBycategoryType(){
        int result = mapper.deleteBycategoryType(101);
        Assert.assertNotNull(result);
    }


    //mapper.xml测试
    @Test
   void  selectByCategoryType(){
        ProductCategory productCategory = mapper.selectByCategoryType(5);
        Assert.assertNotNull(productCategory);
    }

}