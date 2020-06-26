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
                    <form role="form" method="post" action="/seller/category/save">

                        <div class="form-group">
                            <label >类目名称</label>
                            <#--value="${(productInfo.get().productName)!''
                                新增修改双用
                            -->

                            <input name="categoryName" type="text" class="form-control"  value="${(category.categoryName)!''}" />
                        </div>
                        <div class="form-group">
                            <label >类目编号</label>
                            <input name="categoryType" type="number" class="form-control"  value="${(category.categoryType)!''}" />
                        </div>

                        <#--提交隐藏id-->
                        <input type="hidden" name="categoryId" value="${(category.categoryId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
