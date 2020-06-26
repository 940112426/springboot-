package com.imooc.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XuMingyue
 * @create 2020-06-22 9:51
 * 转换
 */
@Slf4j
public class OrderFormToOrderDto {
    public static OrderDto convert(OrderForm orderForm){

        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
       // BeanUtils.copyProperties(); 属性名一致，才能用这个赋值
        Gson gson = new Gson();
        List<OrderDetail> orderDetaillist=new ArrayList<>();

        try {
            orderDetaillist=gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        } catch (Exception e) {
           log.error("【对象转换】 错误，string={},",orderForm.getItems());
           throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDto.setOrderDetailList(orderDetaillist);

        return orderDto;
    }
}
