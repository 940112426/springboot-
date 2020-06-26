package com.imooc.sell.repository;


import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author XuMingyue
 * @create 2020-06-18 23:31
 * 4-1-c.2
 *  //单元测试：选中ProductCategoryRepository右击->go to ->test->Create New Test->OK
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
       Optional<ProductCategory> productCategory=repository.findById(1);
        System.out.println(productCategory.toString());
    }
    @Test
    @Transactional  //测试完自动回滚，数据不会增加
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);     //测试修改
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(2);
        ProductCategory result = repository.save(productCategory);
        //返回结果应该为result,而不是空
        Assert.assertNotNull(result);
    }
    //【需要添加无参构造方法】
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2, 3, 4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        //结果长度大于0成功
        Assert.assertNotEquals(0,result.size());
    }
}