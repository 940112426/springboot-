package com.imooc.sell.dataobject.mapper;


import com.imooc.sell.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author XuMingyue
 * @create 2020-06-24 17:04
 * 类目
 */
public interface ProductCategoryMapper {
    /**
     * (category_name,cotegory_type)  与数据库字段保持一致
     * #{categoryName,jdbcType=VArCHAR} 与map字段一致
     *       map.put("categoryName","京东618");
     *        map.put("category_type",101);
     * aR
     * @param map
     * @return
     */
    @Insert(" insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER})")
    int insertByMap(Map<String,Object> map);

  /**
   * #{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER} * 与实体类中的属性名一致 * @param 【常用】
   * * @return
   * @param productCategory
   * @return
   */
  @Insert(
      " insert into product_category(category_name,category_type) values (#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
  int insertByObject(ProductCategory productCategory);

    /**
     * 查询
     * @param categoryType
     * @return
     */
    @Select("select * from product_category where category_type=#{categoryType}")
    @Results({//返回的结果【category_id：与数据库字段一致，categoryId：与实体类字段一致】
            @Result(column = "category_id", property="categoryId"),
            @Result(column = "category_name" ,property="categoryName"),
            @Result(column = "category_type" ,property="categoryType"),
    })
    ProductCategory findByCategoryType(Integer  categoryType);

    /**
     * 查询多条
     * @param categoryName
     * @return
     */
    @Select("select * from product_category where category_name=#{categoryName}")
    @Results({//返回的结果【category_id：与数据库字段一致，categoryId：与实体类字段一致】
            @Result(column = "category_id", property="categoryId"),
            @Result(column = "category_name" ,property="categoryName"),
            @Result(column = "category_type" ,property="categoryType"),
    })
    List<ProductCategory> findByCategoryName(String  categoryName);

    /**
     * 更新
     * 传多个参数：需用@Param
     * @param categoryName
     * @param categoryType
     * @return
     */
    @Update("update product_category set category_name=#{categoryName} where category_type=#{categoryType}")
    int updateByCategoryType(@Param("categoryName") String categoryName,
                             @Param("categoryType") Integer categoryType);

    /**
     * 删除
     * @param categoryType
     * @return
     */
    @Delete("delete from product_category where category_type=#{categoryType}")
    int deleteBycategoryType(Integer categoryType);


    /**
     * 将sql放入xml中
     * @param categoryType
     * @return
     */
    ProductCategory selectByCategoryType(Integer categoryType);
}
