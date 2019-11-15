<!DOCTYPE html>
<html>
<head>
    <title>student</title>
</head>
<body style="font-size:12px; font-family:SimSun">
学生信息：<br>
学号：${student.id}
姓名：${student.name}
年龄：${student.age}
<br>
学生列表：
<table border="1">
    <tr>
        <th>序号</th>
        <th>学号</th>
        <th>姓名</th>
        <th>年龄</th>
    </tr>
    <!-- 遍历 -->
    <#list stuList as stu>
        <!-- 判断  -->
        <#if stu_index % 2 == 0>
            <tr bgcolor="red">
        <#else>
            <tr bgcolor="green">
        </#if>
        <!--显示条数  -->
        <td>${stu_index}</td>
        <td>${stu.id}</td>
        <td>${stu.name}</td>
        <td>${stu.age}</td>
        </tr>
    </#list>
</table>
<br>

<!-- 可以使用?date,?time,?datetime,?string(parten)-->
当前日期：${date?string("yyyy/MM/dd HH:mm:ss")}<br>
null值的处理：${val!"val的值为null"}<br>
判断val的值是否为null：<br>
<#if val??>
    val中有内容
<#else>
    val的值为null
</#if>
引用模板测试：<br>
<#include "hello.ftl">
</body>
</html>