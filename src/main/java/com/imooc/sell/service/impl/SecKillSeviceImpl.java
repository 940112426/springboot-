package com.imooc.sell.service.impl;

import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.RedisLock;
import com.imooc.sell.service.SecKillService;
import com.imooc.sell.utils.KeyUtil;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XuMingyue
 * @create 2020-06-24 21:20
 * 秒杀接口实现
 */
@Service
public class SecKillSeviceImpl implements SecKillService {
    @Autowired
    private RedisLock redisLock;
    /**
     * 锁超时时间10s
     */
    private static final int  TIMEOUT=10000;
    /**
     * 国庆活动，皮蛋粥特价，限量10000份
     */
    /**
     * 商品信息
     */
    static Map<String ,Integer> products;
    static Map<String ,Integer> stock;
    static Map<String ,String> orders;
    static{
        /**
         * 模拟多个表，商品表，库存表，秒杀成功订单表
         */
        products=new HashMap<>();
        stock=new HashMap<>();
        orders=new HashMap<>();
        products.put("123456",10000);
        stock.put("123456",10000);
    }

    /**
     * 查询
     * @param productId
     * @return
     */
    private String queryMap(String productId){
        return "国庆活动，皮蛋粥特价，限量份"
                +products.get(productId)
                +"还剩："+stock.get(productId)+"份，"
                +"该商品成功下单用户数:"
                +orders.size()+"人";
    }
    @Override
    public String querySecKillProductInfo(String prodcutId){
        return this.queryMap(prodcutId);
    }
    @Override
    public  void orderProductMockDiffUser(String productId){

        //redis加锁
        //定义一个锁住时间
        long time=System.currentTimeMillis()+TIMEOUT;
        //判断加锁是否成功
        if(!redisLock.lock(productId,String.valueOf(time))){
            //没成功
            throw new SellException(101,"加锁没成功，人数太多");
        }

        //查询该商品库存，为0则活动结束
       int  stockNum = stock.get(productId);
        if(stockNum==0){
            throw new SellException(100,"活动结束");
        }else {
            //下单（模拟不同用户openID不同）int
            orders.put(KeyUtil.genUniqueKey(),productId);
            //减库存
            stockNum = stockNum-1;
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }

        //解锁
        redisLock.unlock(productId,String.valueOf(time));
    }
}
