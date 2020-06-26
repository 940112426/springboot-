package com.imooc.sell.exception;

import com.imooc.sell.enums.ResultEnum;
import lombok.Getter;

/**
 * @author XuMingyue
 * @create 2020-06-20 22:34
 * 6-3-b.1  异常处理
 *
 *  //用于捕获SellException异常
 */
@Getter
public class SellException extends  RuntimeException{
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        //把resultEnum.getMessage()内容传入到父类的构造方法中去
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String defaultMessage) {
        super(defaultMessage);
        this.code=code;

    }
}
