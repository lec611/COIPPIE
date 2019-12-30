<%--
  Created by IntelliJ IDEA.
  User: lec
  Date: 2019/12/30
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>总体结果</title>

</head>
<body>
<button id="renderPdf">下载为PDF</button>
    <%
        String subScore = "'"+request.getParameter("subScore")+"'";
        String totalScore = request.getParameter("totalScore").substring(0,5);
        String level = request.getParameter("level");
    %>
    <h1>总得分为：<%=totalScore%></h1>
    <h1>评估等级为：<%=level%></h1>
    <h1>分类得分情况如下：</h1><br>
    <div id = "chartContainer">

    </div>
</body>
<script src='static/js/jquery/jquery.min.js'></script>
<script src="static/js/canvasjs.min.js"></script>
<script src="./static/js/html2canvas.js"></script>
<script src="./static/js/jsPdf.debug.js"></script>
<script>
    $(function () {//柱状图
            var sub  = <%=subScore%>;
            showBarChart(sub);
    });
    function showBarChart(result) {
        var questionType = ["环境","过程","效果","工作与成果"];
        var data = result.split(",");
        charData = [];
        for (var i = 0; i < data.length; i++) {
            var column = {
                type: "column",
                name: questionType[i] + '得分',
                legendText: "" + questionType[i],
                showInLegend: true,
                dataPoints: [
                    {label: " ", y: parseInt(data[i])},
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
                text: '分类得分情况',
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
    var downPdf = document.getElementById("renderPdf");

    downPdf.onclick = function() {
        html2canvas(document.body, {
            background: '#FFFFFF',
            onrendered: function (canvas) {

                var contentWidth = canvas.width;
                var contentHeight = canvas.height;

                //一页pdf显示html页面生成的canvas高度;
                var pageHeight = contentWidth / 592.28 * 841.89;
                //未生成pdf的html页面高度
                var leftHeight = contentHeight;
                //pdf页面偏移
                var position = 0;
                //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
                var imgWidth = 595.28;
                var imgHeight = 592.28 / contentWidth * contentHeight;

                var pageData = canvas.toDataURL('image/jpeg', 1.0);

                var pdf = new jsPDF('', 'pt', 'a4');

                //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
                //当内容未超过pdf一页显示的范围，无需分页
                if (leftHeight < pageHeight) {
                    pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight);
                } else {
                    while (leftHeight > 0) {
                        pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
                        leftHeight -= pageHeight;
                        position -= 841.89;
                        //避免添加空白页
                        if (leftHeight > 0) {
                            pdf.addPage();
                        }
                    }
                }

                pdf.save('content.pdf');
            }
        })

    }
</script>
</html>
