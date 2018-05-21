//全局博主ID
var topFansAuthorId, topReadNumAuthorId;
// 词云基本配置
var wordCloudOption = {
	title : {
		text : '',
		x : 'center'
	},
	tooltip : {
		show : true
	},
	series : [ {
		name : '关键字',
		type : 'wordCloud',
		size : [ '80%', '80%' ],
		textRotation : [ 0, 45, 90, -45 ],
		textPadding : 0,
		autoSize : {
			enable : true,
			minSize : 14
		},
		// Global text style
		textStyle : {
			normal : {
				fontFamily : 'sans-serif',
				fontWeight : 'bold',
				// Color can be a callback function or a color string
				color : function() {
					// Random color
					return 'rgb('
							+ [ Math.round(Math.random() * 160),
									Math.round(Math.random() * 160),
									Math.round(Math.random() * 160) ].join(',')
							+ ')';
				}
			},
			emphasis : {
				shadowBlur : 10,
				shadowColor : '#333'
			}
		},
		data : []
	} ]
};
// 柱状图基本配置
var barGraphOption = {
	title : {
		text : ''
	},
	tooltip : {},
	xAxis : {
		data : [],
		axisLabel : {
			interval : 0,
			rotate : 40
		},
		// 开启x轴点击事件
		triggerEvent : true
	},
	yAxis : {},
	series : []
};

// 折线图基本配置
var lineChartOption = {
	title : {
		text : ''
	},
	legend : {
		data : []
	},
	xAxis : {
		type : 'category',
		data : [],
		axisLabel : {
			interval : 0,
			rotate : 40
		}
	},
	tooltip : {
		show : true
	},
	yAxis : {
		type : 'value'
	},
	series : [ {
		name : '',
		data : [],
		type : 'line'
	} ]
};

/**
 * 1.包括博主年度创建量图、博客年度创建量图、博客年度阅读量图以及其相关的年度博客关键字图
 */
var authorYearCreatedNumChart = echarts.init(document
		.getElementById('author-year-created-num'));
authorYearCreatedNumChart.setOption(lineChartOption);
authorYearCreatedNumChart.showLoading();

var blogYearCreatedNumChart = echarts
		.init(document.getElementById('blog-year-created-num'));
blogYearCreatedNumChart.setOption(lineChartOption);
blogYearCreatedNumChart.showLoading();

var allBlogWordCloud = echarts.init(document.getElementById('all-blog-wordcloud'));
allBlogWordCloud.setOption(wordCloudOption);
allBlogWordCloud.showLoading();

/**
 * 通过博主id以及博主名称设置词云数据
 */
function getAuthorPublishBlogWordCloudData(authorid, authorname, isAsyn) {
	$.ajax({
		type : 'get',
		asyn : isAsyn,
		// get请求的数据,发送id进行请求构造
		data : {
			id : authorid
		},
		url : 'getAuthorBlogTag',
		contentType : 'application/json;charset=utf-8',
		dataType : 'json', // 很关键，否则返回的data为字符串
		// 请求的json，设置
		success : function(data) {
			authorBlogWordCloud.hideLoading();
			authorBlogWordCloud.setOption({
				title : {
					text : '博主发表的博客关键字',
					// 博主昵称的设置
					subtext : authorname
				},
				series : [ {
					data : data.series
				} ]
			});
		}
	});
}

/**
 * 1.博主所写博客的关键字词云 2.词云数据生成依赖于博主Id
 */
var authorBlogWordCloud = echarts.init(document
		.getElementById('author-blog-wordcloud'));
authorBlogWordCloud.setOption(wordCloudOption);
authorBlogWordCloud.showLoading();

/**
 * 1.通过DOM初始化博主粉丝量排行榜 2.设定图表选项为barGraphOption，即柱状图 3.设定xAsix以及series的点击事件方法体
 * 4.方法体对词云数据进行AJAX加载
 */
var topFansAuthorChart = echarts.init(document
		.getElementById('top-fans-author'));
topFansAuthorChart.setOption(barGraphOption);
topFansAuthorChart.showLoading();
topFansAuthorChart.on("click", function(param) {
	authorBlogWordCloud.showLoading();
	var authorid, authorname;
	// 根据点击的是xAsix还是series进行id以及昵称的获取
	if (param.componentType == 'series') {
		authorid = topFansAuthorId[param.dataIndex];
		authorname = param.name;
	} else if (param.componentType == 'xAxis') {
		authorid = topFansAuthorId[param.xAxisIndex];
		authorname = param.value;
	}
	// 词云加载
	getAuthorPublishBlogWordCloudData(authorid, authorname, true);
})

/**
 * 1.博客阅读量最多的博主排行榜表 2.构造如同 粉丝量最多博主排行榜表
 */
var topReadNumAuthorChart = echarts.init(document
		.getElementById('top-read-num-author'));
topReadNumAuthorChart.setOption(barGraphOption);
topReadNumAuthorChart.showLoading();
topReadNumAuthorChart.on("click", function(param) {
	authorBlogWordCloud.showLoading();
	var id, name;
	// 根据点击的是xAsix还是series进行id以及昵称的获取
	if (param.componentType == 'series') {
		id = topReadNumAuthorId[param.dataIndex];
		name = param.name;
	} else if (param.componentType == 'xAxis') {
		id = topReadNumAuthorId[param.xAxisIndex];
		name = param.value;
	}
	// 词云加载
	getAuthorPublishBlogWordCloudData(id, name, true);
})

// 粉丝量表的ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	// get请求的数据
	data : {
		limit : "8"
	},
	url : 'listTopFansAuthor',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		// 全局变量赋值
		topFansAuthorId = data.id;
		// 隐藏加载动画，对Option数据赋值
		topFansAuthorChart.hideLoading();
		topFansAuthorChart.setOption({
			title :{
				text : '博主粉丝量排行榜'
			},
			legend : {
				data : [ '粉丝量', '关注量', '阅读量' ],
				// 粉丝量表主要对粉丝量感兴趣，隐藏其他数据
				selected : {
					'关注量' : false,
					'阅读量' : false
				}
			},
			xAxis : {
				data : data.authorName
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '粉丝量',
				type : 'bar',
				data : data.fans
			}, {
				// 根据名字对应到相应的系列
				name : '关注量',
				type : 'bar',
				data : data.attention
			}, {
				// 根据名字对应到相应的系列
				name : '阅读量',
				type : 'bar',
				data : data.readSum
			} ]
		});
		// 根据粉丝量top1的博主进行第一次加载，同时不使用异步加载，以免出现id没有加载出来的情况
		getAuthorPublishBlogWordCloudData(topFansAuthorId[0],
				topFansAuthorChart.getOption().xAxis[0].data[0], false);
	}
});
// 博主阅读量表ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	data : {
		limit : "8"
	},
	url : 'listTopReadAuthor',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		topReadNumAuthorId = data.id;
		topReadNumAuthorChart.hideLoading();
		topReadNumAuthorChart.setOption({
			title :{
				text : '博文阅读量排行榜'
			},
			legend : {
				data : [ '阅读量', '关注量', '粉丝量' ],
				// 阅读量表主要对阅读量感兴趣，隐藏其他数据
				selected : {
					'关注量' : false,
					'粉丝量' : false
				}
			},
			xAxis : {
				data : data.authorName
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '阅读量',
				type : 'bar',
				data : data.readSum
			}, {
				// 根据名字对应到相应的系列
				name : '关注量',
				type : 'bar',
				data : data.attention
			}, {
				// 根据名字对应到相应的系列
				name : '粉丝量',
				type : 'bar',
				data : data.fans
			} ]
		});
	}
});

//博主创建年度数据的ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	url : 'listYearAuthorCreatedNum',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		authorYearCreatedNumChart.hideLoading();
		authorYearCreatedNumChart.setOption({
			title : {
				text : "博主年度创建量"
			},
			legend : {
				data : [ '创建量' ]
			},
			xAxis : {
				data : data.year
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '创建量',
				data : data.num
			} ]
		});
	}
});
//博客创建的年度数据ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	url : 'listYearBlogCreatedNum',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		blogYearCreatedNumChart.hideLoading();
		blogYearCreatedNumChart.setOption({
			title : {
				text : "博客年度创建量"
			},
			legend : {
				data : [ '创建量' ]
			},
			xAxis : {
				data : data.year
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '创建量',
				data : data.num
			} ]
		});
	}
});
//所有博客关键字ajax获取
$.ajax({
	type : 'get',
	asyn : true,
	url : 'getAllBlogWordCloud',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json',
	success : function(data) {
		allBlogWordCloud.hideLoading();
		allBlogWordCloud.setOption({
			title:{
				text : '所有博客关键字'
			},
			series : [ {
				data : data.series
			} ]
		});
	}
});