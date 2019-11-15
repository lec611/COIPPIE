<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>OICPMPIE - 修改个人信息页面</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <!--全局样式表-->
    <link href="./static/css/global.css" rel="stylesheet"/>
    <%--<link rel="shortcut icon" href="./static/images/Logo_40.png" type="image/x-icon">--%>
    <link rel="stylesheet" href="./static/css/login/font.css">
    <link rel="stylesheet" href="./static/css/login/xadmin.css">

    <style>
        .rePassword, .rePassword:focus {
            border-color: #f44336 !important;
        }

        .disableEmail, .disableEmail:focus {
            pointer-events: none !important;
            cursor: none !important;
            color: #D2D2D2 !important;
            background-color: #9E9E9E !important;
            opacity: 1 !important;
        }
    </style>
</head>

<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message">OICPMPIE - 修改个人信息<div style="text-align: right"><a href="${ctx}/updatePassword">点此修改密码</a></div></div><div></div>
    <div id="darkbannerwrap"></div>
    <form class="layui-form" action="" method="post">
        <input type="text" name="username" id="username"  placeholder="用户名(不可改)" disabled="true"
               autocomplete="off" class="layui-input" >
        <hr class="hr15">
        <input type="text" name="email" id="email" lay-verify="required|email" placeholder="邮箱(不可改)" disabled="true"
               autocomplete="off" class="layui-input">
        <hr class="hr15">
        <input type="password" name="password" id="password" lay-verify="required|password" placeholder="密码(不可改)" disabled="true"
               autocomplete="off" class="layui-input">
        <hr class="hr15">

        <input type="text" name="mobilePhone" id="mobilePhone" lay-verify="required|password" placeholder="手机号(不可改)" disabled="true" autocomplete="off" class="layui-input">
        <hr class="hr15">
        <input type="text" name="sex" id="sex" placeholder="性别" autocomplete="off" class="layui-input">
        <hr class="hr15">
        <input type="text" name="companyName" id="companyName" placeholder="单位名称" autocomplete="off" class="layui-input">
        <hr class="hr15">
        <input type="text" name="companyAddress" id="companyAddress" placeholder="单位地址" autocomplete="off" class="layui-input">
        <hr class="hr15">
        <input type="text" name="researchArea" id="researchArea" placeholder="研究领域" autocomplete="off" class="layui-input">
        <hr class="hr15">
        
        
        <button style="width: 100%" class="layui-btn layui-btn-radius" lay-submit="" lay-filter="submit" onclick="saveChange()">保存修改</button>
        <hr class="hr15" >
    </form>
</div>
<!--遮罩-->
<div class="blog-mask animated layui-hide"></div>
<!-- layui.js -->
<script src="./static/plug/layui/layui.js"></script>
<script src='./static/js/jquery/jquery.min.js'></script>
<script>
    $(function () {
        $.ajax({
            type: 'post',
            url: '${ctx}/userInfo/initShow',
            data: [],
            dataType: 'json',
            success: function (data) {
                var result=eval('('+data+')');

                document.getElementById('username').value=result['name'];
                document.getElementById('email').value=result['email'];
                document.getElementById('password').value=result['password'];
                document.getElementById('mobilePhone').value=result['phoneNum'];
                document.getElementById('sex').value=result['sex'];
                document.getElementById('companyName').value=result['company'];
                document.getElementById('companyAddress').value=result['address'];
                document.getElementById('researchArea').value=result['domain'];
            },
            failure: function (data) {
                alert("获取数据失败！");
            }
        });

    });

    function saveChange() {
        var mobile=document.getElementById('mobilePhone').value;
        var sex=document.getElementById('sex').value;
        var companyName=document.getElementById('companyName').value;
        var companyAddress=document.getElementById('companyAddress').value;
        var researchArea=document.getElementById('researchArea').value;

        $.ajax({
            type: 'post',
            url: '${ctx}/userInfo/updateInfo',
            data: {"phoneNum": mobile,"sex":sex,"company":companyName,"address":companyAddress,"domain":researchArea},
            dataType: 'json',
            success: function () {
                alert("修改成功！");
                //返回主界面
                history.go(-1);
            }
        });
    }


</script>
</body>
</html>