package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-18 23:28
 * DAO层
 * 4-1-c.1
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
  /**
   * //单元测试：选中ProductCategoryRepository右击->go to ->test->Create New Test->OK
   * 通过编目查询多条数据
   *
   * @param categoryTypeList
   * @return
   */
  List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
