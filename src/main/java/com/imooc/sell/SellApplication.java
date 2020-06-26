package com.imooc.sell;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author
 */
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.sell.dataobject.mapper")
@EnableCaching
@Slf4j
public class SellApplication {

    public static void main(String[] args) {
        /**
         * 修改
         */
        log.error("【错误日志】");
        log.error("【错误日志】");
        log.error("【错误日志】");
        log.error("【错误日志】");
        log.info("【信息日志】");
        log.info("【信息日志】");
        log.info("【信息日志】");
        log.info("【信息日志】");
        log.warn("【警告日志】");
        log.warn("【警告日志】");
        log.warn("【警告日志】");
        log.warn("【警告日志】");
        log.trace("【这是啥日志】");
        log.trace("【这是啥日志】");
        log.trace("【这是啥日志】");
        log.trace("【这是啥日志】");
        log.trace("【这是啥日志】");
        log.trace("【这是啥日志】");
        log.trace("【这是啥日志】");
        log.debug("【debug日志】");
        log.debug("【debug日志】");

        SpringApplication.run(SellApplication.class, args);
    }

}
