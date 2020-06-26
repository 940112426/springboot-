package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.utils.ResultVoUtil;
import com.imooc.sell.vo.ProductInfoVo;
import com.imooc.sell.vo.ProductVo;
import com.imooc.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XuMingyue
 * @create 2020-06-19 17:52
 * 5-3-a.0 a.4
 * 买家商品
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    /**
     * 商品信息
     */
    @Autowired
    private ProductService productService;
    /**
     * 商品类目
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * Cacheable：第一次访问，访问方法内容，并设置redis(返回的内容)，下次访问不在访问方法代码
     * @return
     */
    @GetMapping("/list")
    @Cacheable(cacheNames ="product",key="123")
    public ResultVo list(){
        //1.查询所有上架商品
             List<ProductInfo> upProductInfoList = productService.findUpAll();
        //2.查询类目（一次性查询，减少查询时间）
            List<Integer> catetoryTypeList=upProductInfoList.stream()
                    .map(e->e.getCategoryType())
                    .collect(Collectors.toList());
            List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(catetoryTypeList);
        //3.数据拼装
            //3.1用列表存储商品信息
            List<ProductVo> productVoList=new ArrayList<>();
            //3.2遍历商品类目列表
            for (ProductCategory productCategory : productCategoryList) {
                //创建商品对象
                ProductVo productVo = new ProductVo();
                //设置商品的信息：商品类目、类目名称、'商品其他详细信息'
                productVo.setCategeoryType(productCategory.getCategoryType());
                productVo.setCategoryName(productCategory.getCategoryName());

                    //定义列表保存商品详细信息
                    List<ProductInfoVo> productInfoVoList=new ArrayList<>();
                    //遍历商品详细信息列表，并将其设置到商品信息中
                    for (ProductInfo upProductInfo : upProductInfoList) {
                        //如果商品信息中的类目类型和商品类目中的类目类型一致【保证每个信息都一一对应】
                        if(upProductInfo.getCategoryType().equals(productCategory.getCategoryType())){
                            //创建商品详细信息对象
                            ProductInfoVo productInfoVo = new ProductInfoVo();
                            //*****将一个对象里面的属性值【upProductInfo】拷贝到另外一个对象中【productInfoVo】****
                            BeanUtils.copyProperties(upProductInfo,productInfoVo);
                            //将商品详细信息添加到存储商品详细信息的列表中
                            productInfoVoList.add(productInfoVo);
                        }
                    }
                //将商品详细信息设置到商品信息中
                productVo.setProductInfoVos(productInfoVoList);
                //将商品信息存放到商品信息列表中
                productVoList.add(productVo);
            }
        //返回成功的数据
        return ResultVoUtil.success(productVoList);

//        //创建data对象
//        ProductVo productVo = new ProductVo();
//        //创建商品详细信息对象
//        ProductInfoVo productInfoVo = new ProductInfoVo();
//        //将商品详细信息设置到data中
//        productVo.setProductInfoVos(Arrays.asList(productInfoVo));
//        //将data设置到返回对象中
//        resultVo.setData(Arrays.asList(productVo));


    /*  【封装为工具类】
        //创建返回结果对象
        ResultVo resultVo = new ResultVo();
        //将商品信息存放到result中
        resultVo.setData(productVoList);
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        return resultVo;
    */
    }
}
