package com.imooc.sell.controller;

import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.service.SellerService;
import com.imooc.sell.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.CookieStore;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author XuMingyue
 * @create 2020-06-24 10:52
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**登录*/
    @GetMapping("/login")
    public  ModelAndView login(@RequestParam("openid")String openid,
                       HttpServletResponse response,
                       Map<String ,Object> map){
        //1.openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo==null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
            //生成token
        String token = UUID.randomUUID().toString();
        //设置token过期时间  将过期时间定义为常量
        Integer expire = RedisConstant.EXPIRE;

    redisTemplate
        .opsForValue()
        .set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        //3.设置token至cookie---->将token定义为常量
        CookieUtil.set(response, CookieConstant.TOKEN,token,expire);
//       只跳转不渲染（最好用完整地址，不用相对地址）
        return  new ModelAndView("redirect:/seller/order/list");
    }

    /**登出*/
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletResponse response,
                               HttpServletRequest request,
                               Map<String , Object> map){
        //1.从cookie查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie!=null){
//            清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //清除cookie
            CookieUtil.set(response, CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
       return  new ModelAndView("common/success",map);

    }
}
