package com.imooc.sell.form;

import lombok.Data;

/**
 * @author XuMingyue
 * @create 2020-06-24 2:06
 * 用于接收表单传过来的 对象
 */
@Data
public class CategoryForm {
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
}
