package com.imooc.sell.constant;

/**resid常量
 * @author XuMingyue
 * @create 2020-06-24 11:18
 */
public interface RedisConstant {

    /**
     *  设置前缀  token以token_开头
     */

    String TOKEN_PREFIX="token_%s";
    /**
     * 2小时
     */
    Integer EXPIRE=7200 ;
}
