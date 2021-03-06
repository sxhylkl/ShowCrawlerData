<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="generator" content="HTML Tidy for HTML5 for Windows version 5.2.0" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="show blog charts page" />
<meta name="author" content="ccran" />
<link rel="icon" href="${pageContext.request.contextPath}/image/logo.ico" type="img/x-ico" />
<title>csdn</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/css/chart.css" rel="stylesheet" />
</head>
<body>
	<!--导航栏实现-->
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<!--尺寸缩小时对菜单项的隐藏弹出-->
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
				data-target="#navbar" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}">Blog</a>
		</div>
		<div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath}/csdn">csdn</a></li>
					<li><a href="${pageContext.request.contextPath}/cnblogs">cnblogs</a></li>
				</ul>
			</div>
		</div>
	</div>
	</nav>
	<!--图表展示-->
	<div class="container marketing">
		<hr class="featurette-divider" />
		<div class="row featurette">
			<div class="col-md-7">
				<h2 class="featurette-heading">
					csdn信息展示
				</h2>
				<p class="lead">这里主要完成对csdn相关信息的展示</p>
			</div>
			<div class="col-md-5">
				<!-- 所有博客词云 -->
				<div id="all-blog-wordcloud" style="width: 600px; height: 400px;"></div>
			</div>
		</div>
		<hr class="featurette-divider" />
		<div class="row featurette">
			<div class="col-md-7">
				<div id="year-blog-num" style="width: 600px; height: 400px;"></div>
			</div>
			<div class="col-md-5">
				<div id="year-blog-main-topic" style="width: 600px; height: 400px;"></div>
			</div>
		</div>
		<hr class="featurette-divider" />
		<div class="row featurette">
			<div class="col-md-7">
				<div id="top-rank-author" style="width: 600px; height: 400px;"></div>
			</div>
			<div class="col-md-5">
				<div id="author-blog-wordcloud" style="width: 600px; height: 400px;"></div>
			</div>
		</div>
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/echarts.js"></script>
	<script src="${pageContext.request.contextPath}/js/echarts-wordcloud.js"></script>
	<!-- 自定义js -->
	<script src="${pageContext.request.contextPath}/js/chart-option.js"></script>
	<script src="${pageContext.request.contextPath}/js/chart-function.js"></script>
	<script src="${pageContext.request.contextPath}/js/csdn.js"></script>
</body>
</html>