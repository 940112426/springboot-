package com.imooc.sell;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author XuMingyue
 * @create 2020-06-18 19:02
 */
@RunWith(SpringRunner.class) //import org.junit.runner.RunWith;
@SpringBootTest             //import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
public class LoggerTest {
    //没有使用  @slf4j
//    private final Logger logger=  LoggerFactory.getLogger(LoggerTest.class);
    @Test
    public void test1(){

//        logger.debug("debug...");
//        logger.info("info...");
//        logger.error("error...");
        //使用@Slf4j
            String name="immc";
            String password="123456";
            log.debug("debug...");
            log.error("error...");
            log.info("name:{}, password:{}",name,password);

    }

}
