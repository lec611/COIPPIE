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
<body>
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
                    <li><a href="#" style="color: #0C0C0C">关于软件</a></li>


                    <li class="divider"></li>
                    <li><a href="#" style="color: #0C0C0C">使用说明</a></li>
                </ul>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="Feedback"><a href="${ctx}/feedback.jsp" style="color: black;">意见反馈</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="informationService"><a href="${ctx}/informationService.jsp" style="color: black;">信息查询</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="evaluation"><a href="${ctx}/evaluation.jsp" style="color: black;">评估分析</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item">
                <button type="button" class="btn btn-default" id="informationSurvey"><a href="${ctx}/informationSurvey.jsp" style="color: black;">信息调查</a></button>
            </li>
            <li class="dropdown pull-right layui-nav-item" >
                <button type="button" class="btn btn-default" id="establishFile"><a href="${ctx}/establishFile.jsp" style="color: black;">建立档案</a></button>
            </li>
        </ul>
        <ul class="layui-nav layui-layout-right head-nav-right">
            <div class="btn-group" style="margin-top:15px">
                <button type="button" class="btn btn-default" id="loginButton"><a href="${ctx}/login.jsp">登录</a></button>
                <button type="button" class="btn btn-default" id="registerButton"><a href="${ctx}/register.jsp">注册</a></button>
                <label class="btn" style="display:none" id="userInfoButton" href="${ctx}/userInfo.jsp"></label>
                <label class="btn" id="logoutButton" style="display: none;" onclick="logout();">退出登录</label>
            </div>
        </ul>

    </div>

    <div class="layui-side left-nav-index" style="width: 20%;left:10%">
        <div><h3><label class="label label-default"> 规划实施环境评估 </label></h3><br></div>
        <div><h3><label class="label label-default"> 规划实施过程评估 </label></h3><br></div>
        <div><h3><label class="label label-default"> 规划实施效果评估 </label></h3><br></div>
        <div><h3><label class="label label-default"> 规划工作与成果评估 </label></h3><br></div>
        <div><h3><label class="label label-default"> 总体评估报告 </label></h3><br></div>
    </div>
    <div class="layui-body left-nav-body" style="left:30%;width: 50%;">
        <div style="height: 60%;width:50%;">
            <div style="margin-top: 20px;"><h4> 规划实施环境评估报告 <a onclick="checkResult('environment')">（点击查看）</a> </h4><br></div>
            <div style="margin-top: 20px;"><h4> 规划实施过程评估报告 <a onclick="checkResult('process')">（点击查看）</a></h4><br></div>
            <div style="margin-top: 20px;"><h4> 规划实施效果评估报告 <a onclick="checkResult('effect')">（点击查看）</a></h4><br></div>
            <div style="margin-top: 20px;"><h4> 规划工作与成果评估报告 <a onclick="checkResult('result')">（点击查看）</a></h4><br></div>
            <div style="margin-top: 20px;"><h4> 规划实施总体评估报告 <a onclick="checkResult('total')">（点击查看）</a></h4><br></div>
        </div>
        <div style="width:50%;">
            <button class="btn btn-default">打印报告（P）</button>
            <button class="btn btn-default">关闭（C）</button>
        </div>
    </div>
    <div style="width: 20%;margin-left:60%;height: auto;height: 60%" id="chartContainer">

    </div>

</div>

</body>
<script src="static/js/canvasjs.min.js"></script>
<script src="https://cdn.bootcss.com/html2canvas/0.5.0-beta4/html2canvas.js"></script>
<script>
    $(function () {
        checkUserLogin();
    });

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

    function checkResult(type) {
        $.ajax({
           type:"post",
           url:"${ctx}/answer/evaluation",
            datatype:"json",
            data:{"type":type},
            success:function (data) {
               if("未提交相应问卷"==data){
                   alert(data);
               }
               showBarChart(data);
            }
        });
    }

    function showBarChart(result) {//柱状图
        var data = eval('(' + result + ')');
        charData = [];
        for (var i = 0; i < data["question"].length; i++) {
            var column = {
                type: "column",
                name: data['question'][i] + '得分',
                legendText: "" + data['question'][i],
                showInLegend: true,
                dataPoints: [
                    {label: " ", y: parseInt(data['score'][i])},
                ]
            };
            charData.push(column);
            //空列
            var column = {
                type: "column",
                showInLegend: false,
                dataPoints: [{}]
            };
            charData.push(column);
        }

        var chart = new CanvasJS.Chart("chartContainer", {
            animationEnabled: true,
            title: {
                text: '柱状图',
                x:'center'
            },
            toolTip: {
                shared: true
            },
            legend: {
                cursor: "pointer",
                itemclick: toggleDataSeries
            },
            data: charData,
        });
        chart.render();

        function toggleDataSeries(e) {
            if (typeof (e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                e.dataSeries.visible = false;
            } else {
                e.dataSeries.visible = true;
            }
            chart.render();
        }
    }

</script>
</html>
