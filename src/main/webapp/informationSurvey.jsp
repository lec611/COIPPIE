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
    <title>COIPPIE</title>
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
            <div class="layui-logo doc-logo" style="font-weight: bold">COIPPIE</div>
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
        <div><h2><a class="label label-default"> 规划实施环境 </a></h2><br></div>
        <div><h2><a class="label label-default"> 规划实施过程 </a></h2><br></div>
        <div><h2><a class="label label-default"> 规划实施效果 </a></h2><br></div>
        <div><h2><a class="label label-default" onclick="showQuestions()"> 规划工作与成果 </a></h2><br></div>
    </div>
    <div class="layui-body left-nav-body" style="left:30%;width: 70%;">
        <div style="margin-top:3%;height: 90%;font-size: large" id="content">

        </div>
        <div>
            <button class="btn btn-default">保存（S）</button>
            <button class="btn btn-default">重新填写（R）</button>
            <button class="btn btn-default">关闭（C）</button>
            <button class="btn btn-default">提交（S）</button>
        </div>
    </div>

</div>

</body>
<script>

    function showQuestions() {
        var data={
            "questions":["国家外交关系","合作国宏观政治形势与社会环境稳定程度"],
            "options":[["全天候战略合作伙伴关系","全面战略协作伙伴关系","全面战略合作伙伴关系","全面战略伙伴关系"],["十分稳定","比较稳定","一般","不稳定"]],
            "type":1,
            "values":[90,90,90,80]
        }

        if(data['type']==1){//多选题
            var htmlStr="";
            for(var j=0;j<data['questions'].length;j++){
                htmlStr+="<b>"+data['questions'][j]+"</b><br>";
                for(var i=0;i<data['options'][j].length;i++){
                    htmlStr+='<input type="checkbox" name="options'+j+'">'+data['options'][j][i];
                }
                htmlStr+='<br>';
            }
            $("#content").html(htmlStr);
        }
        else if(data['type']==0){//单选题
            var htmlStr="";
            for(var j=0;j<data['questions'].length;j++){
                htmlStr+="<b>"+data['questions'][j]+"</b><br>";
                for(var i=0;i<data['options'][j].length;i++){
                    htmlStr+='<input type="radio" name="options'+j+'">'+data['options'][j][i];
                }
                htmlStr+='<br>';
            }
            $("#content").html(htmlStr);
        }
        else if(data['type']==2){//填空题
            var htmlStr="";
            for(var j=0;j<data['questions'].length;j++){
                htmlStr+="<b>"+data['questions'][j]+"</b> &nbsp <input type='text' class='layui-input' style='width: 60%'>";
                htmlStr+='<br>';
            }
            $("#content").html(htmlStr);
        }
    }

</script>
</html>
