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
                    <#--提交地址-->
                    <form role="form" method="post" action="/seller/product/save">

                        <div class="form-group">
                            <label >名称</label>
                            <#--value="${(productInfo.get().productName)!''
                                新增修改双用
                            -->

                            <input name="productName" type="text" class="form-control"  value="${(productInfo.get().productName)!''}" />
                        </div>
                        <div class="form-group">
                            <label >价格</label>
                            <input name="productPrice" type="text" class="form-control"  value="${(productInfo.get().productPrice)!''}" />
                        </div>
                        <div class="form-group">
                            <label >库存</label>
                            <input name="productStock" type="number" class="form-control"  value="${(productInfo.get().productStock)!''}" />
                        </div>
                        <div class="form-group">
                            <label >描述</label>
                            <input name="productDescription" type="text" class="form-control"  value="${(productInfo.get().productDescription)!''}" />
                        </div>

                        <div class="form-group">
                            <label >图片</label>
                            <img width="100" height="100"  src="${(productInfo.get().productIcon)!''}" alt="">
                            <input name="productIcon" type="text" class="form-control"  value="${(productInfo.get().productIcon)!''}" />
                        </div>

                        <div class="form-group">
                            <label >类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <option value="${category.categoryType}"
                                    <#--被选中则到第一个
                                        ??  :存在
                                    -->
                                            <#--类目存在，并且与传过来的类目相等-->
                                            <#if (productInfo.get().categoryType) ?? &&
                                            productInfo.get().categoryType == category.categoryType>
                                                selected
                                            </#if>
                                    >
                                        ${category.categoryName}
                                    </option>
                                </#list>
                            </select>
                        </div>

                        <#--提交隐藏id-->
                        <input type="hidden" name="productId" value="${(productInfo.get().productId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
