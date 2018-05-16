<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cnblog作者信息</title>
<!-- 导入JQuery和Echarts库 -->
<script src="${pageContext.request.contextPath}/js/echarts.js"></script>
<!-- 引用 https://code.jquery.com/jquery-3.1.1.min.js -->
<script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
</head>

<body>
	<div class="listJson">data</div>
	<br />
	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	<div id="main" style="width: 600px; height: 400px;"></div>
	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('main'));

		// 指定图表的配置项和数据
		var option = {
			title : {
				text : "cnblog作者信息"
			},
			tooltip : {},
			legend : {
				data : [ '粉丝量' ]
			},
			xAxis : {
				data : []
			},
			yAxis : {},
			series : [ {
				name : '粉丝量',
				type : 'bar',
				data : []
			} ]
		};
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);

		//使用ajax请求
		$.ajax({
			type : 'get',
			asyn : false,
			url : '${pageContext.request.contextPath }/listTopFans',
			contentType : 'application/json;charset=utf-8',
			dataType : 'json', //很关键，否则返回的data为字符串
			//请求的json，设置
			success : function(data) {
				$(".listJson").text(JSON.stringify(data));
				myChart.setOption({
					xAxis : {
						data : data.authorName
					},
					series : [ {
						// 根据名字对应到相应的系列
						name : '粉丝量',
						data : data.fans
					} ]
				});
			}
		});
	</script>
</body>
</html>