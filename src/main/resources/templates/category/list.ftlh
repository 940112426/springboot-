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
                            <th>类目ID</th>
                            <th>名字</th>
                            <th>Type</th>
                            <th> 创建时间</th>
                            <th> 修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                            <#list categoryList as category>
                                <tr>
                                   <td>${category.categoryId}</td>
                                   <td>${category.categoryName}</td>
                                   <td>${category.categoryType}</td>
                                   <td>${category.createTime}</td>
                                   <td>${category.updateTime}</td>
                                   <td>
                                       <a href="/seller/category/index?categoryId=${category.categoryId}">修改</a>
                                   </td>

                                </tr>
                            </#list>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </div>
</div>


</body>
</html>
