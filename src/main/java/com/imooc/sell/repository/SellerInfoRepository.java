package com.imooc.sell.repository;

import com.imooc.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author XuMingyue
 * @create 2020-06-24 9:54
 * dao
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo , String> {
    /**
     * 根据微信id查找卖家信息
     * @param openid
     * @return
     */
    SellerInfo findByOpenid(String openid);
}
