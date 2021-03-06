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
    <link href="static/css/test.css" rel="stylesheet">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 引入顺序也要注意下,bootstrap.js 依赖于jQuery.js -->
    <script src='static/js/jquery/jquery.min.js'></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>

    <style>
        .inputStyle{
            border-top:0;
            border-rigtht:0;
            border-left: 0;
            border-bottom: #0C0C0C 1px solid;
            width: 200px;
        }
    </style>

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
            <div style="margin-left:60px;width: fit-content;"><h2><label class="label label-default">园区基本信息</label></h2></div>
            <form action="${ctx}/document/insert">
                <div style="font-size:20px;border: 2px solid;margin-left:60px;margin-right:60px;padding:10px;"><!--method="post" enctype="multipart/form-data"-->
                    园区名称<input type="text" class="inputStyle" name ="parkName" required="required"> 建区年份<input type="text" class="inputStyle" name="constructionYear"required="required">  所在国家<input type="text" class="inputStyle" name="country"><br>
                    规划产业<input type="text" class="inputStyle" name="planningIndustry">   规划规模<input type="text" class="inputStyle" name="planningScale"> (km²)   评估规模（规划）<input type="text" class="inputStyle" name="evaluationScale">(km²)<br>
                    实建规模<input type="text" class="inputStyle" name="constructionScale">  (km²)   投资单位<input type="text" class="inputStyle" name="investmentUnit"required="required">   建设单位<input type="text" class="inputStyle" name="constructionUnit"><br>运营单位<input type="text" class="inputStyle" name="operatingUnit"><br>
                    总体规划平面图<br>
                    <input class="btn btn-default" type="file" name="images" onchange="viewImage(this)" id="fileupload" accept="image/*">
                    <div id="localimage" style="width: 200px;height: 200px;border:1px dashed;text-align: center;vertical-align: center"><img src="/wp-content/uploads/2014/06/download.png" class="img-thumbnail" id="preview"></div>
                    <button class="btn btn-default" name="clear" onclick="clearImage()">清除</button>
                </div>
                <hr style="margin-left:60px;margin-right:60px;">
                <div style="margin-left:60px;width: fit-content;"><h2><label class="label label-default">评估者信息</label></h2></div>
                <div style="font-size:20px;border: 2px solid;margin-left:60px;margin-right:60px;padding: 10px;">
                    姓名<input type="text" class="inputStyle" name="personName" required="required"> 职业<input type="text" class="inputStyle" name="job">   工作单位<input type="text" class="inputStyle" name="workAddress">   手机号码<input type="text" class="inputStyle" name="phone"><br>
                    电子邮件<input type="text" class="inputStyle" name="email"> 通讯地址<input type="text" class="inputStyle" name="address">
                </div>

                <hr style="margin-left:60px;margin-right:60px;">
                <div style="margin-left: 60px;text-align: center;margin-top:20px;">
                    <button type="submit" class="btn btn-default" id="saveBtn">保存（S）</button>
                    <button class="btn btn-default" id="closeBtn">关闭（C）</button>
                </div>
            </form>
        </div>
        <div class="clear"></div>
    </div>

</div>
</body>
<script>

    $(function () {
        isLogin();
        checkUserLogin();
    });
    //登陆后操作
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

    //upload the image and preview in the box
    function viewImage(file){
        var preview = document.getElementById('preview');
        if(file.files && file.files[0]){
            //火狐下
            preview.style.display = "block";
            // preview.style.width = "300px";
            // preview.style.height = "120px";
            preview.src = window.URL.createObjectURL(file.files[0]);
        }else{
            //ie下，使用滤镜
            file.select();
            var imgSrc = document.selection.createRange().text;
            var localImagId = document.getElementById("localImage");
            //必须设置初始大小
            localImagId.style.width = "250px";
            localImagId.style.height = "200px";
            try{
                localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
                locem("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
            }catch(e){
                alert("您上传的图片格式不正确，请重新选择!");
                return false;
            }
            preview.style.display = 'none';
            document.selection.empty();
        }
        return true;
    }

    //clear the image
    function clearImage(){
        var preview=document.getElementById("preview");
        preview.src="";

        var obj = document.getElementById('fileupload') ;
        obj.outerHTML=obj.outerHTML;
    }

</script>
</html>
