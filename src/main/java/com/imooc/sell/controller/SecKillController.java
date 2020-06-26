package com.imooc.sell.controller;

import com.imooc.sell.service.SecKillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

/**
 * @author XuMingyue
 * @create 2020-06-24 21:18
 * 秒杀活动controller
 */
@Slf4j
@RestController
@RequestMapping("/skill")
public class SecKillController {
    @Autowired
    private SecKillService secKillService;
    /**
     * 查询秒杀活动叶家商品
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId){
        return secKillService.querySecKillProductInfo(productId);
    }
    /**
     * 秒杀，没有抢到获得xxx,抢到了返回剩余库存
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId){
        log.info("@skill request , productId:"+productId);
        //秒杀
        secKillService.orderProductMockDiffUser(productId);
        //查询还剩余库存
        return secKillService.querySecKillProductInfo(productId);
    }
}
