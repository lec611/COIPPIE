<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
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

    <style type="text/css">
        .foEvaluate{
            text-align: left;
            margin-left:16%;
            margin-right: 16%;
        }
        .picDiv{
            margin-top:20px;
            text-align: center;
            margin-left:16%;
        }
        .foDescribe{
            margin-top:20px;
            margin-left:16%;
            margin-right:16%;
            border: #1E9FFF 1px solid;
            text-align: center;
            clear: left;
        }
        .evaluateDiv{
            margin-top: 20px;
            margin-left: 16%;
            margin-right:16%;
            text-align: center;
            clear: left;
        }
    </style>

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
        <ul class="layui-nav layui-layout-left head-nav-left">
            <li class="dropdown pull-right layui-nav-item">
                <a href="#" data-toggle="dropdown" class="dropdown-toggle">帮助</a>
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

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <div class="blog-main">
            <div class="foEvaluate"><h2>评估流程：</h2></div>
            <div>
                <div class="picDiv">
                    <div style="float: left"><a href="establishFile.jsp"><img src="static/images/establishFile.png"></a><br><h4><b>建立档案</b></h4></div><img src="static/images/pic1.png" style="float: left;margin-top: 40px;">
                    <div style="float: left"><a href="informationSurvey.jsp"><img src="static/images/informationSurvey.png"></a><br><h4><b>信息调查</b></h4></div><img src="static/images/pic1.png" style="float: left;margin-top: 40px;">
                    <div style="float: left"><a href="evaluation.jsp"><img src="static/images/evaluation.png"></a><br><h4><b>评估分析</b></h4></div><img src="static/images/pic1.png" style="float: left;margin-top: 40px;">
                    <div style="float: left"><a href="informationService.jsp"><img src="static/images/informationService.png"></a><br><h4><b>信息查询</b></h4></div>
                </div>
                <div class="foDescribe">
                    <h2>适用单位</h2>
                    <h4>境外产业园区管理单位、投资单位、规划设计与咨询单位、建设单位、运营单位等</h4>
                </div>
                <div class="evaluateDiv">
                    <button class="btn btn-default" onclick="window.location='${ctx}/establishFile.jsp'">开始评估</button>
                </div>
            </div>
            <div id="showPdf"></div>

            <div><span class="share">立即分享</span></div>
        </div>
        <div class="clear"></div>
    </div>

</div>
<!--二维码弹层-->
<div id="popQRCode">
    <div id="qrcode" style="margin-left: 30px; margin-top: 8px;"></div>
</div>
</body>

<script src="static/plug/layui/layui.js"></script>
<script src='static/js/jquery/jquery.min.js'></script>
<script src='static/js/pdfobject.js'></script>
<script src="static/plug/qrcodejs/qrcode.js"></script>
<script src="static/js/canvasjs.min.js"></script>
<script src="https://cdn.bootcss.com/html2canvas/0.5.0-beta4/html2canvas.js"></script>
<script src="https://cdn.bootcss.com/jspdf/1.3.4/jspdf.debug.js"></script>
<!-- ECharts单文件引入 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script>

    // js全局变量
    var userInfo = {};
    userInfo.id = "${user.id}";
    userInfo.name = "${user.name}";
    userInfo.email = "${user.email}";

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

</script>
</html>