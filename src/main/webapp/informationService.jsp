<%--
  Created by IntelliJ IDEA.
  User: 458
  Date: 2019/10/19/019
  Time: 16:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; Charset=gb2312">
    <meta http-equiv="Content-Language" content="zh-CN">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>
    <title>OICPMPIE</title>
    <link rel="stylesheet" href="static/css/layui.css">
    <!--font-awesome-->
    <link href="static//css/font-awesome.min.css" rel="stylesheet"/>
    <!--全局样式表-->
    <link href="static/css/global.css" rel="stylesheet"/>
    <%--分页样式表--%>
    <link href="static/css/page.css" rel="stylesheet">
    <%--index页样式--%>
    <link href="static/css/index.css" rel="stylesheet">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 引入顺序也要注意下,bootstrap.js 依赖于jQuery.js -->
    <script src='static/js/jquery/jquery.min.js'></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header position: absolute;">
        <a href="${ctx}/index.jsp">
            <div class="layui-logo doc-logo" style="font-weight: bold">OICPMPIE</div>
        </a>
        <ul class="layui-nav layui-layout-left small-head-nav-left">
            <li class="layui-nav-item"><a href="javascript:;"></a></li>
        </ul>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left head-nav-left" style="margin-top: 15px;">
            <li class="dropdown pull-right layui-nav-item">
                <a href="#" data-toggle="dropdown" class="dropdown-toggle" style="margin-top: -15px;">帮助</a>
                <ul class="dropdown-menu">
                    <li><a onclick="alert('软件名称：Web境外国际合作园区总体规划实施评估软件（简称OICPMPIE）\n'+
                                          '软件版本号：V1.0\n'+
                                          '开发机构：东南大学\n'+
                                          '开发人员：王兴平、赵胜波、赵四东、施一峰、胡雪峰\n'+
                                          '编程人员：戚晓芳、刘恩赐，徐成龙，喻学乐，贺黎，高建祥，周广振\n')" ;href="#" style="color: #0C0C0C">关于软件</a></li>
                    <li class="divider"></li>
                    <li><a href="${ctx}/guide.jsp" style="color: #0C0C0C">使用说明</a></li>
                    <li class="divider"></li>
                    <li><a onclick="alert('管理员：赵胜波\n邮箱：viczhao2nj@163.com')"; href="#" style="color: #0C0C0C">联系管理员</a></li>
                </ul>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="Feedback"><a href="${ctx}/feedback.jsp"
                                                                               style="color: black;">意见反馈</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="informationService"><a
                        href="${ctx}/informationService.jsp" style="color: black;">信息查询</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="evaluation"><a href="${ctx}/evaluation.jsp"
                                                                                 style="color: black;">评估分析</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="informationSurvey"><a
                        href="${ctx}/informationSurvey.jsp" style="color: black;">信息调查</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="establishFile"><a href="${ctx}/establishFile.jsp"
                                                                                    style="color: black;">建立档案</a>
                </button>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right head-nav-right">
            <div class="btn-group" style="margin-top:15px">
                <button type="button" class="btn btn-default" id="loginButton"><a href="${ctx}/login.jsp">登录</a>
                </button>
                <button type="button" class="btn btn-default" id="registerButton"><a href="${ctx}/register.jsp">注册</a>
                </button>
                <label class="btn" style="display:none" id="userInfoButton" href="${ctx}/userInfo.jsp"></label>
                <label class="btn" id="logoutButton" style="display: none;" onclick="logout();">退出登录</label>
            </div>
        </ul>

    </div>

    <div class="layui-body left-nav-body">
        <!-- 内容主体区域 -->
        <div class="blog-main">
            <!-- 左侧搜索查询界面 -->
            <div class="blog-main-left">
                <div style="margin: 30px;">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <h3><label class="label label-default">查询条件：</label></h3>
                            <select id="condition" class="layui-input">
                                <option>园区名称</option>
                                <option>建区年份</option>
                                <option>投资单位</option>
                            </select>
                        </li>
                        <li class="list-group-item" style="margin-top: 8%">
                            <h3><label class="label label-default">输入查询：</label></h3>
                            <input type="text" id="inputTxt" placeholder="输入查询" class="layui-input">
                        </li>
                    </ul>
                </div>
            </div>
            <!--右侧搜索结果展示界面-->
            <div class="blog-main-right">
                <div style="margin-top: 30px;">
                    <ul class="list-group">
                        <li class="list-group-item">
                            <h3><label class="label label-default">查询结果：</label></h3>
                            <table class="table" id="queryResultTable">
                                <td>园区名称</td>
                                <td>建区年份</td>
                                <td>投资单位</td>
                                <td></td>
                            </table>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <div style="text-align: center;"><!--margin-bottom: 0px;margin-top:-160px;-->
            <button class="btn btn-default" id="queryButton" onclick="doClickQuery()">点击查询（S）</button>
            <button class="btn btn-default" style="margin-left: 6%">关闭（C）</button>
        </div>
    </div>

</div>
<script>

    $(function () {
        isLogin();
        checkUserLogin();
    });
    //登陆后再操作
    function isLogin() {
        var name = "<%=session.getAttribute("name")%>";
        if(name == 'null'){
            alert('请登录后操作');
            window.location='${ctx}/index.jsp';
        }
    }

    // 显示用户信息
    function checkUserLogin() {
        var name = "<%=session.getAttribute("name")%>";
        var ticket = "<%=session.getAttribute("ticket")%>";

        if (name != null && ticket != null && name != "null" && ticket != "null") {
            document.getElementById('loginButton').style.display = 'none';
            document.getElementById('registerButton').style.display = 'none';
            document.getElementById('userInfoButton').style.display = 'block';
            document.getElementById('logoutButton').style.display = 'block';
            var html = "";
            html = html + '<a href="${ctx}/userInfo.jsp;">欢迎' + name + '</a>';
            $("#userInfoButton").html(html);
        } else {
            document.getElementById('loginButton').style.display = 'block';
            document.getElementById('registerButton').style.display = 'block';
            document.getElementById('userInfoButton').style.display = 'none';
            document.getElementById('logoutButton').style.display = 'none';
        }
    }

    // 用户退出
    function logout() {
        $.ajax({
            type: "get",
            url: "${ctx}/reglogin/logout",
            success: function (data) {
                document.getElementById('loginButton').style.display = 'block';
                document.getElementById('registerButton').style.display = 'block';
                document.getElementById('userInfoButton').style.display = 'none';
                document.getElementById('logoutButton').style.display = 'none';
                if (data.code != 200) {
                    layer.msg(data.msg, {icon: 2});
                    return false;
                } else {
                    layer.msg(data.msg, {icon: 1});
                    location = "${ctx}/";
                }
            }
        });
    }

    function doClickQuery() {
        var resultTable = document.getElementById('queryResultTable');
        // 删除所有行，不删除标题行
        var rowCount = resultTable.rows.length; // 获得一共多少行，因为不删除标题，所以索引从 1 开始
        for (var i = 1; i < rowCount; i++) {
            resultTable.deleteRow(1); // 因为删除一行以后下面的行会顶上来，所以一直删除第一行即可
        }
        //查询条件
        var Obj = document.getElementById("condition");
        var index = Obj.selectedIndex;
        var condition = Obj.options[index].text;
        if (condition == "园区名称") {
            condition = "park";
        } else if (condition == "建区年份") {
            condition = "year";
        } else if (condition == "投资单位") {
            condition = "invest";
        }
        //查询关键字
        var key = document.getElementById("inputTxt").value;
        $.ajax({
            type: 'get',
            url: '${ctx}/answer/query',
            data: {"condition": condition, "key": key},
            dataType: "json",
            success: function (data) {
                var re = /user/;
                if (re.test(data)) {
                    var objs = eval(data); // 解析JSON
                    var count = 1;
                    for (var i = 0; i < objs.length; i++) { // 循环对象
                        var queryObj = objs[i];
                        var row = resultTable.insertRow(count++); // 插入一行rows是一个数组，索引从0开始
                        row.insertCell(0).innerHTML = "&nbsp;" + queryObj.park; // insertCell插入列，从0开始
                        row.insertCell(1).innerHTML = "&nbsp;" + queryObj.year;
                        row.insertCell(2).innerHTML = "&nbsp;" + queryObj.invest;
                        var buttonHTML = "<button class=\"layui-btn layui-btn-normal layui-btn-xs\" onclick=\"downloadReport('" + queryObj.user + "','" + queryObj.park + "','" + queryObj.year + "','" + queryObj.invest + "')\" style=\"margin-top: 3px\">下载评估报告 </button>";
                        row.insertCell(3).innerHTML = "&nbsp;" + buttonHTML;
                    }
                } else if (data == '[]'){
                    alert("未查询到结果！");
                } else {
                    alert("请先登录！");
                }
            }
        });
    }

    function downloadReport(user, park, year, invest) {
        $.ajax({
            type: 'post',
            url: '${ctx}/answer/service',
            data: {"user": user, "park": park, "year": year, "invest": invest},
            dataType: "json",
            success: function (data) {
                download();
                alert(data);
            }
        });

    }

    function download() {
        window.location.replace("${ctx}/answer/download");
    }

</script>
</body>
</html>
