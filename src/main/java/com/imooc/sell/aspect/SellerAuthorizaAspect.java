package com.imooc.sell.aspect;

import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author XuMingyue
 * @create 2020-06-24 12:21
 * aop身份验证
 */
@Slf4j
@Aspect
@Component
public class SellerAuthorizaAspect {
    private final StringRedisTemplate redisTemplate;

    public SellerAuthorizaAspect(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * execution(public * com.imooc.sell.controller.SellerUserController
     * Seller 以Seller开头的controller
     *    *(..)):该Controller开头的controller 里面的方法，
     *    【注意，本身的登录登出也是Seller，但是不用验证，将其过滤】
     *   && !execution  ：并且将登录登出controller排除
     */
    @Pointcut(value = "execution(public * com.imooc.sell.controller.Seller*.*(..))&& !execution(public * com.imooc" +
            ".sell.controller.SellerUserController.*(..))")
    public  void verify(){}//验证

    /**
     * 在切入点之前
     */
    @Before("verify()")
    public void  doVerity(){//方法具体实现
        //获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie==null){
            //为登录
            log.warn("【登录校验】 Cookie中查不到token");
            throw  new SellerAuthorizeException();
        }
        //去redis查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            //redis里面没有该token
            log.warn("【登录校验】 Redis中差不到token");
            throw new  SellerAuthorizeException();
        }
    }
}
