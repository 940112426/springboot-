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
                                <th>订单</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th> 地址</th>
                                <th>金额</th>
                                <th> 订单状态</th>
                                <th> 支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list orderDtoPage.content as orderDto>
                                <tr>
                                    <td>${orderDto.orderId}</td>
                                    <td>${orderDto.buyerName}</td>
                                    <td>${orderDto.buyerPhone}</td>
                                    <td>${orderDto.buyerAddress}</td>
                                    <td>${orderDto.orderAmount}</td>
                                    <td>${orderDto.oderStatusEnum.getMessage()}</td>
                                    <td>${orderDto.payStatusEnum.getMessage()}</td>
                                    <td>${orderDto.createTime}</td>
                                    <td>
                                        <a href="/seller/order/detail?orderId=${orderDto.orderId}">详情</a>
                                    <td>
                                        <#if orderDto.getOderStatusEnum().message =="新订单">
                                            <a href="/seller/order/cancel?orderId=${orderDto.orderId}">取消</a>
                                        </#if>
                                        <#if orderDto.getOderStatusEnum().message =="完结">
                                            <button type="button" class="btn btn-sm btn-success active disabled">订单已完结</button>
                                        </#if>
                                        <#if orderDto.getOderStatusEnum().message =="已取消">
                                            <button type="button" class="btn btn-sm btn-warning active disabled">订单已取消</button>
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
                            <#list 1..orderDtoPage.getTotalPages() as index>
                                <#if currentPage==index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                    <li><a href="/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                            <#--currentPage gte orderDtoPage.getTotalPages()  当前页>=总页数-->
                            <#if currentPage gte orderDtoPage.getTotalPages()>
                                <li class="disabled">
                                    <a href="#">下一页</a>
                                </li>
                            <#else>
                                <li>
                                    <a href="/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a>
                                </li>
                            </#if>

                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#--新订单提示弹窗-->
    <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        提醒
                    </h4>
                </div>
                <div class="modal-body">
                    【您有新的订单】
                </div>
                <div class="modal-footer">
<#--   onclick="javascript:document.getElementById('notice').onpause(); 点击关闭按钮，停止播放音乐-->
                    <button type="button" class="btn btn-default" data-dismiss="modal"
                            onclick="javascript:document.getElementById('notice').onpause()">关闭</button>
                    <button type="button" class="btn btn-primary" onclick="location.reload()">查看新的订单</button>
                </div>
            </div>
        </div>
    </div>
<#--播放音乐 loop循环-->
    <audio id="notice" loop="loop">
        <source src="mp3/song.mp3" type="audio/mpeg" />
    </audio>


<#--    Jquery要在第一个-->
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    /*消息推送js*/
    //判断浏览器是否支持
    var webSocket=null;
    if('WebSocket' in window){
        //支持
        webSocket=new WebSocket("ws://localhost:8080/webSocket");
    }else{
        alert("该浏览器不支持websocket");
    }
    webSocket.onopen=function (event) {
        console.log("建立连接");
    }
    webSocket.onclose=function (event) {
        console.log("连接关闭");

    }
    webSocket.onmessage=function (event) {
        console.log("收到消息："+event.data);
        //弹出提醒，播放音乐
        $('#myModal').modal('show');
        document.getElementById('notice').onplay();
    }
    webSocket.onerror=function () {
        alert("websocket通信发生错误！");
    }
    //窗口关闭时，同时关闭webSocket
    window.onbeforeunload=function () {
        webSocket.close();
    }
</script>
</body>
</html>
<#--<#list orderDtoPage.content as orderDto>-->
<#--    ${orderDto.orderId}<br>-->
<#--</#list>-->