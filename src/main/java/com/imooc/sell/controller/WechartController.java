package com.imooc.sell.controller;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

/**
 * @author XuMingyue
 * @create 2020-06-22 22:17
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechartController {
    @Autowired
    private WxMpService wxMpService;
    @GetMapping("/auth")
    public  String authorize(@RequestParam("resultUrl")String resultUrl){
        String url="http://sell.com/wechat/userInfo";
       String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url,WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(resultUrl));
       log.info("【微信网页授权】获取code result={}",redirectUrl);
        return "rediect:"+redirectUrl;

    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,
                         @RequestParam("state")String returnUrl){
        WxMpOAuth2AccessToken wxMpAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}",e);
            e.printStackTrace();
        }
        String openId = wxMpAuth2AccessToken.getOpenId();
        return "rediret:"+returnUrl+"?openid="+openId;
    }
}
