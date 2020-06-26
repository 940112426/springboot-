package com.imooc.sell.converter;

import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dto.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XuMingyue
 * @create 2020-06-21 17:15
 * 6-3-b.4.1 orderMaster转为OrderDto
 */
public class OrderMasterToOrderDto {
    public static OrderDto convert(OrderMaster orderMaster){
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return  orderDto;
    }
    public static List<OrderDto> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream()
                .map(e->convert(e))
                .collect(Collectors.toList());
    }
}
