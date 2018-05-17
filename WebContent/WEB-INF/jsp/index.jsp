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
<title>Blog</title>
<!-- Bootstrap core CSS -->
<link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/css/cover.css" rel="stylesheet" />
</head>
<body>
	<div class="site-wrapper">
		<div class="site-wrapper-inner">
			<div class="cover-container">
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

				<!--中间文字-->
				<div class="inner cover">
					<h1 class="cover-heading">博客数据图表可视化</h1>
					<p class="lead">SSM+BootStrap+Echarts</p>
					<div class="lead">
						<!--bootstrap栅栏系统-->
						<div class="row">
							<div class="col-sm-6">
								<p>100,000,000</p>
								<h3>博客量</h3>
							</div>
							<div class="col-sm-6">
								<p>100,000,000</p>
								<h3>博主量</h3>
							</div>
						</div>
					</div>
				</div>
				<!--底部-->
				<div class="mastfoot">
					<div class="inner">
						<p>
							Cover template for <a href="https://v3.bootcss.com/examples/cover/">Bootstrap</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>