package com.imooc.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author XuMingyue
 * @create 2020-06-19 17:55
 * 5-3--a.1
 * 在使用redis缓存时，如果出现ResultVo不能序列化，则继承implements Serializable 即可
 * http请求返回的最外层对象
 */
@Data
public class ResultVo<T> implements  Serializable{
    private static final long serialVersionUID = -8476247260949512575L;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回具体内容
     */
    private T data;
}
