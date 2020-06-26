package com.imooc.sell.controller;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author XuMingyue
 * @create 2020-06-23 13:35
 */
@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderService orderService;
    /**
     * 订单列表
     * @param page  第几页，从第一页开始
     * @param size  每显示数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "3") Integer size,
                             Map<String,Object> map){
        PageRequest request =  PageRequest.of(page - 1, size);
        Page<OrderDto> orderDtoPage = orderService.findList(request);
        map.put("orderDtoPage",orderDtoPage);
        //当前页
        map.put("currentPage",page);
        map.put("size",size);

    System.out.println("【成功完成热部署】");
        return new ModelAndView("order/list",map);
    }
    @GetMapping("/cancel")
    public ModelAndView cannel(@RequestParam("orderId") String orderId,
                               Map<String ,Object> map){

        try {
            OrderDto  orderDto = orderService.findByOrderId(orderId);
            orderService.cancel(orderDto);
        } catch (SellException e) {
            log.error("【卖家端取消订单】 发生异常",e );
            map.put("msg", e.getMessage());
            //取消失败返回订单列表界面
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success");
    }

    /**
     * 定单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        OrderDto orderDto = new OrderDto();
        try {
            orderDto = orderService.findByOrderId(orderId);
        } catch (SellException e) {
            log.error("【卖家端查询订单详情】 发生异常，",e);
            map.put("msg",e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/error",map);

        }
        map.put("orderDto",orderDto);
        return new ModelAndView("/order/detail",map);
    }


    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        try {
            OrderDto orderDto = orderService.findByOrderId(orderId);
            orderService.finish(orderDto);
        } catch (SellException e) {
           log.error("【卖家端完结订单】 发生异常{}",e);
           map.put("msg",e.getMessage());
           map.put("url","/seller/order/list");
           return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        map.put("url","/seller/order/list");
        return new ModelAndView("common/success");
    }
//    @RequestMapping("/autherror")
//    public ModelAndView authError(){
//        return new ModelAndView("/order/detail");
//    }
}
