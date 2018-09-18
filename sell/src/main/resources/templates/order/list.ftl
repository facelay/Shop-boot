<html>

    <#include "../common/header.ftl">

    <body>

    <div id="wrapper" class="toggled">

            <#--边栏sidebar-->
            <#include "../common/nav.ftl"/>

            <#--主要内容-->
            <div id="page-content-wrapper">
                <div class="container-fluid">
                <div class="row clearfix">
                    <div class="col-md-12 column">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>订单ID</th>
                                <th>姓名</th>
                                <th>手机号</th>
                                <th>地址</th>
                                <th>金额</th>
                                <th>订单状态</th>
                                <th>支付状态</th>
                                <th>创建时间</th>
                                <th colspan="2">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                        <#list orderDTOPage.content as order>
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.buyerName}</td>
                            <td>${order.buyerPhone}</td>
                            <td>${order.buyerAddress}</td>
                            <td>${order.orderAmount}</td>
                            <td>${order.getOrderStatusEnum().message}</td>
                            <td>${order.getPayStatusEnum().message}</td>
                            <td>${order.createTime}</td>
                            <td>
                                <a href="/sell/seller/order/detail?orderId=${order.orderId}">详情</a>
                            </td>
                            <td>
                                    <#if order.getOrderStatusEnum().message =="新订单">
                                        <a href="/sell/seller/order/cancel?orderId=${order.orderId}">取消</a>
                                    </#if>
                            </td>
                        </tr>
                        </#list>
                            </tbody>
                        </table>
                    </div>

                <#--分页-->
                    <div id="paging" class="pagination pull-right"></div>

                <#--
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">上一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage - 1}">上一页</a></li>
                        </#if>
                        <#if orderDTOPage.getTotalPages() gte 1>
                            <#list 1..orderDTOPage.getTotalPages() as index>
                                <#if currentPage=index>
                                <li class="disabled"><a href="#">${index}</a></li>
                                <#else>
                                <li><a href="/sell/seller/order/list?page=${index}">${index}</a></li>
                                </#if>
                            </#list>
                        </#if>
                        <#if currentPage gte orderDTOPage.getTotalPages()>
                            <li class="disabled"><a href="#">下一页</a></li>
                        <#else>
                            <li><a href="/sell/seller/order/list?page=${currentPage + 1}">下一页</a></li>
                        </#if>
                    </ul>
                </div> -->
                </div>




            </div>
            </div>

        </div>
    </body>
    <script>
        layui.use('laypage', function () {
            var laypage = layui.laypage;
            //执行一个laypage实例
            laypage.render({
                elem: 'paging',
                limit: ${pageSize},
                curr: ${currPage},
                count: ${orderDTOPage.getTotalElements()},
                jump: function (obj, first) {
                    //首次不执行
                    if (!first) {
                        location.href = "/sell/seller/order/list?pageNo=" + obj.curr + "&pageSize=" + obj.limit
                    }
                }
            });
        });
    </script>
</html>
