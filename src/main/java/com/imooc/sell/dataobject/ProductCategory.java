package com.imooc.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;



@Entity     /**将数据库映射为对象*/
@DynamicUpdate  /**动态更新时间*/
@Data      /**使用这个可以不用写get/set tostring方法*/
/**
 * @author 94011
 * @author XuMingyue
 * @create 2020-06-18 23:22
 * 4-1-c.0
 */
public class ProductCategory {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) /**自增*/
    /**
     * 类目id
     */
    private Integer categoryId;
    /**
     * 类目名字
     */
    private String  categoryName;
    /**
     * 类目编号
     */
    private Integer categoryType;

    private Date createTime;
    private Date updateTime;

    public ProductCategory() {
    }
    public ProductCategory(String categoryName,Integer categoryType) {
        this.categoryName=categoryName;
        this.categoryType=categoryType;
    }

}
