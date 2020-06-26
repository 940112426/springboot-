package com.imooc.sell.service;

import com.imooc.sell.dataobject.SellerInfo;

/**
 * @author XuMingyue
 * @create 2020-06-24 10:18
 *
 */
public interface SellerService {
    /**
     * 根据微信id查询卖家信息
     * @param openid
     * @return
     */
    SellerInfo findSellerInfoByOpenid(String openid);
}
