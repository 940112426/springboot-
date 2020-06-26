package com.imooc.sell.utils;

import com.imooc.sell.enums.CodeEnum;

/**
 * @author XuMingyue
 * @create 2020-06-23 14:36
 */
public class EnumUtil {
    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass){
        for (T each : enumClass.getEnumConstants()) {
            //如果相等，返回枚举
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
