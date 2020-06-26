<#--<h1>${orderDtoPage.content}</h1>-->
<html>
<#include  "../common/header.ftlh">
<body>

<#--引用侧边栏-->
<div id="wrapper" class="toggled">
    <#--边栏sidebar-->
    <#include "../common/nav.ftlh">

    <#--主要内容-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>商品ID</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th> 单价</th>
                            <th>库存</th>
                            <th> 描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list productInfoPage.content as productInfo>
                                <tr>
                                   <td>${productInfo.productId}</td>
                                   <td>${productInfo.productName}</td>

                                   <td>
                                       <img height="50" width="50" src=" ${productInfo.productIcon}" alt="">
                                   </td>

                                   <td>${productInfo.productPrice}</td>
                                   <td>${productInfo.productStock}</td>
                                   <td>${productInfo.productDescription}</td>
                                   <td>${productInfo.categoryType}</td>
                                   <td>${productInfo.createTime}</td>
                                   <td>${productInfo.updateTime}</td>
                                    <td>
                                        <a href="/seller/product/index?productId=${productInfo.productId}">修改</a>
                                    <td>
                                        <#if productInfo.getProductStatusEnum().message =='上架'>
                                             <a href="/seller/product/offsale?productId=${productInfo.productId}">下架</a>
                                        <#else >
                                                 <a href="/seller/product/onsale?productId=${productInfo.productId}">上架</a>
                                         </#if>

                                    </td>
                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>
                <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#--currentPage lte 1当前页<=1-->
                        <#if currentPage lte 1>
                            <li class="disabled">
                                <a href="#">上一页</a>
                            </li>
                        <#else >
                            <li>
                                <a href="/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a>
                            </li>
                        </#if>

                        <#--0..orderDtoPage.getTotalPages()  :表示0-总页数-->
                        <#list 1..productInfoPage.getTotalPages() as index>
                            <#if currentPage==index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                        <#--currentPage gte orderDtoPage.getTotalPages()  当前页>=总页数-->
                        <#if currentPage gte productInfoPage.getTotalPages()>
                            <li class="disabled">
                                <a href="#">下一页</a>
                            </li>
                        <#else>
                            <li>
                                <a href="/seller/product/list?page=${currentPage+1}&size=${size}">下一页</a>
                            </li>
                        </#if>

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
