package com.imooc.sell.handler;

import com.imooc.sell.exception.SellException;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.utils.ResultVoUtil;
import com.imooc.sell.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author XuMingyue
 * @create 2020-06-24 13:44
 *捕获异常
 */
@ControllerAdvice
public class SellExceptionHandler {
   //这里涉及到ProjectUrlConfig

    /**
     * 拦截登录异常  SellerAuthorizeException自定义的类
     * @return
     */
    @ExceptionHandler(value = SellerAuthorizeException.class )
    public ModelAndView  handlerAuthorizeException(){
        //
        return new ModelAndView("common/saoma");
    }

  /**
   * 13-1 * 捕获SellException异常
   * @param e
   * @return
   */
  @ExceptionHandler(value = SellException.class)
  @ResponseBody
  public ResultVo handlerSellerExption(SellException e) {
        return ResultVoUtil.error(e.getCode(),e.getMessage());
    }


}
