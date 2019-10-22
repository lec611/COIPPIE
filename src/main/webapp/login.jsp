<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>COIPPIE - 登陆页面</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4,initial-scale=0.8,target-densitydpi=low-dpi"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--全局样式表-->
    <link href="static/css/global.css" rel="stylesheet"/>
    <!--Layui-->
    <link href="static/plug/layui/css/layui.css" rel="stylesheet"/>
    <link rel="shortcut icon" href="static/images/Logo_40.png" type="image/x-icon">
    <link rel="stylesheet" href="static/css/login/font.css">
    <link rel="stylesheet" href="static/css/login/xadmin.css">
    
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	
	<!-- 引入顺序也要注意下,bootstrap.js 依赖于jQuery.js -->
	<script src='static/js/jquery/jquery.min.js'></script>
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    
</head>

<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message">COIPPIE - 登录</div>
    <div id="darkbannerwrap"></div>
    <form class="layui-form" action="" method="post">
        <input type="text" name="nameEmail" id="nameEmail" placeholder="请输入用户名/邮箱"
               autocomplete="off" class="layui-input">
        <hr class="hr15">
        <input type="password" name="password" id="password" placeholder="请输入密码"
               autocomplete="off" class="layui-input">
        <hr class="hr15">
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <input type="text" name="codeCaptcha" id="codeCaptcha" placeholder="请输入验证码"
                       autocomplete="off" class="layui-input">
            </div>
            <label class="field-wrap" style="cursor:pointer;">
                <img src="" id="captchaImg" align='absmiddle' height="40px"
                     style="margin-top: 5px" onclick="changeCaptcha()">
            </label>
            <span id="code_span" style="color: green"></span>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-inline">
                <p style="text-align: left"><a href="${ctx}/register">没有账号？前往注册</a></p>
            </div>
            <label class="field-wrap" style="cursor:pointer;">
                <p style="text-align: right"><a href="${ctx}/findPassword">忘记密码？</a></p>
            </label>
        </div>
        <button style="width: 100%;" class="layui-btn layui-btn-radius" lay-filter="submit" lay-submit="">登录</button>
    </form>
</div>
<!-- layui.js -->
<script src="static/plug/layui/layui.js"></script>
<script src='static/js/jquery/jquery.min.js'></script>
<script>
    var next = "${next}";
    $(function () {
        changeCaptcha();
    });
    // 更换验证码
    function changeCaptcha() {
        $.get('${ctx}' + '/reglogin/codeCaptcha', function (data) {
            $("#captchaImg").attr('src', 'data:image/jpeg;base64,' + data.data.image);
        });
    }
    layui.use(['form', 'layer'], function() {
        var form = layui.form;
        var layer = layui.layer;
        var $ = layui.jquery;

        function checkLoginInfo(nameEmail, password, codeCaptcha) {
            if (nameEmail.trim() == "" || nameEmail.trim() == null) return "请输入用户名/邮箱！";
            if (password == "" || password == null) return "请输入密码！";
            if (codeCaptcha == "" || codeCaptcha == null) return "请输入验证码！";
            return "";
        }

        //监听提交
        form.on('submit(submit)', function(){
            var nameEmail = $("#nameEmail").val();
            var password = $("#password").val();
            var codeCaptcha = $("#codeCaptcha").val();

            var hint = checkLoginInfo(nameEmail, password, codeCaptcha);
            if (hint != "") {
                layer.msg(hint, {icon:2});
                return false;
            }

            $.ajax({
                type: 'post',
                url: '${ctx}/reglogin/login',
                data: {"nameEmail": nameEmail, "password": password, "codeCaptcha": codeCaptcha},
                dataType: 'json',
                success: function (data) {
                    if (data.code !== 200) {
                        alert("登陆失败，请重新登录！");
                        layer.msg(data.msg,{icon: 2});
                        changeCaptcha();
                        return false;
                    } else {
                        if (next != null && next.trim() != "") {
                            location = next;
                        } else {
                            location = "${ctx}/";
                        }
                    }
                }
            });
            return false;
        });
    });

</script>
</body>
</html>