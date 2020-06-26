package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author XuMingyue
 * @create 2020-06-23 20:20
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    /**
     * 查找商品订单列表
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "2")Integer size,
                             Map<String ,Object> map){

        //查找分页信息
        PageRequest request = PageRequest.of(page-1, size);
        ////分页查询所有商品
        Page<ProductInfo> productInfoPage= productService.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("/product/list",map);
    }

    @RequestMapping("/onsale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productService.onSale(productId);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/list");
           return  new ModelAndView("common/error",map);
        }
        map.put("url","/seller/product/list");
//        map.put("msg",)
        return new ModelAndView("common/success",map);
    }
    @RequestMapping("/offsale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try {
            productService.offSale(productId);
        } catch (SellException e) {
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/list");
            return  new ModelAndView("common/error",map);
        }
        map.put("url","/seller/product/list");
//        map.put("msg",)
        return new ModelAndView("common/success",map);
    }

    /**
     * 新增
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("index")
    public ModelAndView index(@RequestParam(value = "productId",required = false)String productId,
                      Map<String,Object> map){
        if(!StringUtils.isEmpty(productId)){
            Optional<ProductInfo> productInfo = productService.findByProductId(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }

  /**
   * 保存\更新
   * CachePut:每次都执行，并将返回的内容存入redis
   * CacheEvict在访问方法之后会先清除redis之前的缓存，然后在将结过存储到redis
   * @param form
   * @param bindingResult
   * @param map
   * @return
   */
  @PostMapping("/save")
  /** @CachePut(cacheNames = "product",key = "123") */
  @CacheEvict(cacheNames = "product", key = "123")
  public ModelAndView save(
      @Valid ProductForm form, BindingResult bindingResult, Map<String, Object> map) {
        //判断传过来的参数是否出错
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            //新增商品页面
            map.put("url","/seller/product/index");
            return  new ModelAndView("common/error",map);
        }
        Optional<ProductInfo> productInfo = Optional.of(new ProductInfo());
        try {

            //如果ProductId不为空-->修改
            if(!StringUtils.isEmpty(form.getProductId())){
                //查询商品信息
                productInfo= productService.findByProductId(form.getProductId());
            }else{
                //为空--》新增  设置商品id
                form.setProductId(KeyUtil.genUniqueKey());
            }
            //没出错进行数据拷贝
            //ProductInfo proInfo = new ProductInfo();
            //将form数据拷贝到productInfo  【get()必须加】
            BeanUtils.copyProperties(form,productInfo.get());
            productService.save(productInfo.get());
        } catch (SellException e) {
            //保存出错
            map.put("msg",e.getMessage());
            map.put("url","/seller/product/index");
            return  new ModelAndView("common/error",map);
        }
        map.put("url","/seller/product/list");
        return new ModelAndView("common/success",map);
    }

}
