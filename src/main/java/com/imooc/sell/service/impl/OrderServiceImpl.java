package com.imooc.sell.service.impl;

import com.imooc.sell.converter.OrderMasterToOrderDto;
import com.imooc.sell.dataobject.OrderDetail;
import com.imooc.sell.dataobject.OrderMaster;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.repository.OrderDetailRespository;
import com.imooc.sell.repository.OrderMasterRepository;
import com.imooc.sell.repository.ProductInfoRepository;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.service.WebSocket;
import com.imooc.sell.utils.KeyUtil;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author XuMingyue
 * @create 2020-06-20 22:27
 *  6-3-b.0 订单接口实现
 */
@Service
@Slf4j
public class OrderServiceImpl  implements OrderService {

    /**
     *
     *    用于查询单个商品信息   private ProductService productService 用于查询单个商品信息
     *  根据商品id查找商品 Optional<ProductInfo> findByProductId(String productId)
     */
    @Autowired
    private ProductInfoRepository productInfoRepository;
    /**
     * 调用这个加减库存
     */
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRespository orderDetailRespository;
    @Autowired
    private  OrderMasterRepository orderMasterRepository;

    /**
     * 引入websocket推送消息
     */
    @Autowired
    private WebSocket webSocket;

    /**
     * 定义总价
     *  @Transactional   事务，当订单出现异常，则进行回滚
     */
    BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO) ;
    @Override
    @Transactional
    public OrderDto create(OrderDto orderDto) {
        //定义总价
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO) ;
        //订单创建时生成订单id
        String orderId=KeyUtil.genUniqueKey();
        //1.查询商品(数量、价格)
            //遍历订单详情列表
            for (OrderDetail orderDetail:orderDto.getOrderDetailList() ) {
                //根据订单详情中的商品id获取单个商品详细信息
                Optional<ProductInfo> productInfo = productInfoRepository.findByProductId(orderDetail.getProductId());
                //获取的信息为空,抛出异常   【!productInfo.isPresent()】
                if(productInfo == null || !productInfo.isPresent() ){
                    throw new SellException(ResultEnum.PRODUCT_NOT_EXITS);
                }
                //2.计算订单总价=订单价格*数量
                orderAmount=productInfo.get().getProductPrice()
                        .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                        .add(orderAmount);
                //3.写入订单详情入库(orderDetail)
                    //设置生成随机订单详情id
                orderDetail.setDetailId(KeyUtil.genUniqueKey());
                orderDetail.setOrderId(orderId);
                    //将商品详细信息，拷贝到订单详细中
                BeanUtils.copyProperties(productInfo.get(),orderDetail);
                orderDetailRespository.save(orderDetail);

            }
        //3.写入订单入库（orderMaster)
            //创建订单对象
        OrderMaster orderMaster = new OrderMaster();
            //如果属性值为null,也会拷贝进去，所以可以先拷贝再赋值
        orderDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderDto,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        //orderStatus和payStatus默认值为0，拷贝后变为null,需要重新赋值
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIL.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存[在package com.imooc.sell.service.ProductService;中，完成加减库存方法]
       //得到 遍历出订单中的订单数量，即购物车中的商品数量，
        List<CartDto> cartDtoList=orderDto.getOrderDetailList().stream()
                .map(e->new CartDto(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        //遍历出的数量，即为该商品应该减少的数量
        productService.decreaseStock(cartDtoList);


        /**
         * websocket推送消息
         */
        webSocket.sendMessage("【有新的订单】");
        webSocket.sendMessage(orderDto.getOrderId());
        return orderDto;
    }

    @Override
    public OrderDto findByOrderId(String orderId) {
        //查询订单
        OrderMaster orderMaster = orderMasterRepository.findByOrderId(orderId);
        if(orderMaster ==null){
            throw  new SellException(ResultEnum.ORDER_NOT_XIST);
        }
        //查询订单详情
        List<OrderDetail> orderDetails = orderDetailRespository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetails)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_XIST);
        }
        OrderDto orderDto = new OrderDto();
        //将orderMaster，赋值到orderDto
        BeanUtils.copyProperties(orderMaster,orderDto);
        orderDto.setOrderDetailList(orderDetails);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        //根据微信id找到个多商品，并分页查询
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        //将orderMaster转为orderDto
        List<OrderDto> orderDtoList = OrderMasterToOrderDto.convert(orderMasterPage.getContent());
        //
        PageImpl<OrderDto> orderDtoPage = new PageImpl<>(orderDtoList, pageable, orderMasterPage.getTotalElements());
        return orderDtoPage;
    }

    /**
     *
     * @param orderDto
     * @return
     */
    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            //如果生成的订单id与事先设定为新订单的id
            log.error("【取消订单】 订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
            //因为订单状态是在orderMaster中，所以需要先将orderDto数据复制到orderMaster中
        OrderMaster orderMaster = new OrderMaster();
                //设置订单为取消状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
                //注意先修改在拷贝，这样拷贝的数据才不是null
        BeanUtils.copyProperties(orderDto,orderMaster);
                 //保存订单信息
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
                //判断是否更新成功
        if(updateResult==null){

        }
        //返回库存
            //如果得到订单详情为空
        if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDto={} ",orderDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
            //如果订单中有该商品，则取消成功，将库存数量增加
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
                .map(e -> new CartDto(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
            //增加库存数量
        productService.increateStock(cartDtoList);

        //如果已经支付，需要退款
        if(orderDto.getOrderStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO  支付成功，则退款
        }
        //
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto finish(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】 订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【完结订单】更新失败，orderMaster={}",orderMaster);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        //判断订单状态
        if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【订单支付完成】订单状态不正确  orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIL.getCode())){
            log.error("【订单支付完成】订单状态不正确  orderDto==>",orderDto);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto,orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if(result==null){
            log.error("【支付订单】更新失败，orderMaster={}",orderMaster);
            throw  new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findAll(pageable);
        List<OrderDto> orderDtoList = OrderMasterToOrderDto.convert(orderMasterPage.getContent());

        return new PageImpl<>(orderDtoList,pageable,orderMasterPage.getTotalElements());
    }
}
