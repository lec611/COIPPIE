<%--
  Created by IntelliJ IDEA.
  User: lec
  Date: 2019/10/11
  Time: 18:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <link rel="stylesheet" href="static/plug/layui/css/layui.css">
    <%--网页图标--%>
    <link rel="shortcut icon" href="static/images/COIPIB.png" type="image/x-icon">
    <!--font-awesome-->
    <link href="static/plug/font-awesome/css/font-awesome.min.css" rel="stylesheet"/>
    <!--全局样式表-->
    <link href="static/css/global.css" rel="stylesheet"/>
    <%--分页样式表--%>
    <link href="static/css/pageInfo/page.css" rel="stylesheet">
    <%--index页样式--%>
    <link href="static/css/index.css" rel="stylesheet">
    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 引入顺序也要注意下,bootstrap.js 依赖于jQuery.js -->
    <script src='static/js/jquery/jquery.min.js'></script>

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    <style>
        .bias1{
            border:1px solid;
            margin-top: 23px;
            -webkit-transform: rotate(15deg);/*Safari 4+,Google Chrome 1+  */
            -moz-transform: rotate(15deg);/*Firefox 3.5+*/
            filter: progid:DXImageTransform.Microsoft.BasicImage(Rotation=0.15);
        }
        .bias2{
            border:1px solid;
            margin-top: 23px;
            -webkit-transform: rotate(20deg);/*Safari 4+,Google Chrome 1+  */
            -moz-transform: rotate(20deg);/*Firefox 3.5+*/
            filter: progid:DXImageTransform.Microsoft.BasicImage(Rotation=0.2);
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
                <a href="#" onClick="javascript:parameter();return false;">使用说明</a>
            </li>
            <!--禁止删除-->
            <li class="layui-nav-item" id="adminMenu">

            </li>
        </ul>
        <ul class="layui-nav layui-layout-right head-nav-right">
            <button type="button" class="btn btn-primary btn-lg" style="font-size:12px;margin-right:20px">
                <span class="glyphicon glyphicon-user"></span>
            </button>
        </ul>

        <a class="small-doc-navicon" href="javascript:;" onclick="showLeftNav();">
            <i class="fa fa-navicon"></i>
        </a>
    </div>
    <div style="margin-left: 50px">
        <h2>使用说明下载</h2>
        普通用户可以点击下载按钮下载使用说明<br>
        仅有管理员可以上传使用说明文件<br>
    </div>
    <div class="blog-main">
        <div style="margin-left: 20px;margin-right: 50px;" id="parameterDiv">
            <div style="text-align: center;margin-left:30px;position: relative">
                <input type="file" id="inputFile" class="form-control" name="inputFile" style="width: 80%;float: left">
            </div>
        </div>
        <br><br><br>
        <div style="text-align: center">
            <button class="layui-btn" lay-submit="" lay-filter="formSearch" onclick="uploadGuideFile()" id="1" style="color:white">上传说明文件</button>
            <button class="layui-btn" lay-submit="" lay-filter="formSearch" id="2" style=""><a href="${ctx}/guideFile/download" style="color: white">下载说明文件</a></button>
        </div>
    </div>
</div>

</body>
<script>
    //上传说明文件(需管理员权限且只能是PDF文件)
    function uploadGuideFile(){
        var formData = new FormData();
        formData.append('file', $('#inputFile')[0].files[0]); // 固定格式

        $.ajax({
            url:'${ctx}/guideFile/upload',	//后台接收数据地址
            data:formData,
            type: "POST",
            dataType: "json",
            cache: false,			//上传文件无需缓存
            processData: false,		//用于对data参数进行序列化处理 这里必须false
            contentType: false,
            success: function(result){
                if(result === "success"){
                    alert("说明文件上传成功！");
                }else if(result === "error"){
                    alert("非管理员用户无上传权限！");
                }else{
                    alert("注意：上传文件格式必须为PDF！");
                }
            },
            failure: function (data) {
                alert(data+"\n文件传输出错！");
            }
        })
    }

</script>
</html>
