package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-19 13:37
 * 4-3-c service 接口实现测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired
    private  CategoryServiceImpl categoryService;
    @Test
     public void getOne() {
        ProductCategory productCategory = categoryService.getOne(1);
        //如果返回值为期待值1，则成功
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> categories = categoryService.findAll();
        Assert.assertNotEquals(0,categories.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        Assert.assertNotEquals(0,categoryList.size());
    }

    @Test
    void save() {
        ProductCategory productCatetory = categoryService.save(new ProductCategory("女生最爱", 4));
        ProductCategory result = categoryService.save(productCatetory);
        Assert.assertNotNull(result);
    }
}
