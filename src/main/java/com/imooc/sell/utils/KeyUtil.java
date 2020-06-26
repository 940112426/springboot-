package com.imooc.sell.utils;

import java.sql.SQLOutput;
import java.util.Random;

/**
 * @author XuMingyue
 * @create 2020-06-20 22:50
 * 6-3-b.2生成订单索引方法
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * synchronized：避免多线程时重复
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        //900000生成6位数
        Integer number = random.nextInt(900000) + 100000;
        return  System.currentTimeMillis()+String.valueOf(number);
    }

}
