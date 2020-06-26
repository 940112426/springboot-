package com.imooc.sell.service;

/**
 * @author XuMingyue
 * @create 2020-06-24 21:20
 * 秒杀接口
 */
public interface SecKillService {

    /**
     * 查询秒杀商品信息
     * @param productId
     * @return
     */
    String querySecKillProductInfo(String productId);

    /**
     * 秒杀
     * @param productId
     */
    void orderProductMockDiffUser(String productId);
}
