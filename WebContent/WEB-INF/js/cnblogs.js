//全局ID
var topFansId, topReadAuthorId;
// 基于准备好的dom，初始化echarts实例
var topFansChart = echarts.init(document.getElementById('top-fans-author'));
var topFansBlogTagChart = echarts.init(document
		.getElementById('top-fans-author-blog-tag'));
var authorCreatedNumChart = echarts.init(document
		.getElementById('author-created-num'));
var readTopAuthorChart = echarts.init(document
		.getElementById('read-top-author'));
// 粉丝量排行博主的博客阅读图表配置
var fansBogTagChartOption = {
	title : {
		text : '博客主题',
		subtext: '博主昵称',
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
// 粉丝量排行图表配置
var fansChartOption = {
	title : {
		text : "最受欢迎博主"
	},
	tooltip : {},
	legend : {
		data : [ '粉丝量', '关注量', '阅读量' ],
		selected : {
			'关注量' : false,
			'阅读量' : false
		}
	},
	xAxis : {
		data : [],
		axisLabel : {
			interval : 0,
			rotate : 40
		},
		//开启x轴点击
		triggerEvent: true
	},
	yAxis : {},
	series : [ {
		name : '粉丝量',
		type : 'bar',
		data : []
	}, {
		name : '关注量',
		type : 'bar',
		data : []
	}, {
		name : '阅读量',
		type : 'bar',
		data : []
	} ]
};
// 阅读量排行图表配置
var readTopChartOption = {
	title : {
		text : "博客被阅读最多博主"
	},
	tooltip : {},
	legend : {
		data : [ '阅读量', '关注量', '粉丝量' ],
		selected : {
			'关注量' : false,
			'粉丝量' : false
		}
	},
	xAxis : {
		data : [],
		axisLabel : {
			interval : 0,
			rotate : 40
		},
		//开启x轴点击
		triggerEvent: true
	},
	yAxis : {},
	series : [ {
		name : '阅读量',
		type : 'bar',
		data : []
	}, {
		name : '关注量',
		type : 'bar',
		data : []
	}, {
		name : '粉丝量',
		type : 'bar',
		data : []
	} ]
};
// 创建年份图表配置
var createNumOption = {
	title : {
		text : "博主创建量"
	},
	legend : {
		data : [ '创建量' ]
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
		name : '创建量',
		data : [],
		type : 'line'
	} ]
};
// 使用刚指定的配置项和数据显示图表,同时设置加载动画
authorCreatedNumChart.setOption(createNumOption);
authorCreatedNumChart.showLoading();

topFansChart.setOption(fansChartOption);
//点击后进行ajax请求，更新图云
topFansChart.on("click", function (param) {
	topFansBlogTagChart.showLoading();
	var id,subtext;
	//根据点击的是xAsix还是series进行id以及昵称的获取
	if(param.componentType=='series'){
		id=topFansId[param.dataIndex];
		subtext=param.name;
	}else if(param.componentType=='xAxis'){
		id=topFansId[param.xAxisIndex];
		subtext=param.value;
	}
	// 发表博客主题的ajax
	$.ajax({
		type : 'get',
		//嵌套ajax同步标志设置为false，避免出现数据未加载情况
		asyn : true,
		//get请求的数据,发送id进行请求构造
		data : {
			id : id
		},
		url : 'getAuthorBlogTag',
		contentType : 'application/json;charset=utf-8',
		dataType : 'json', // 很关键，否则返回的data为字符串
		// 请求的json，设置
		success : function(data) {
			topFansBlogTagChart.hideLoading();
			topFansBlogTagChart.setOption({
				title : {
					//博主昵称的设置
					subtext: subtext
				},
				series : [ {
					data : data.series
				} ]
			});
		}
	});
})
topFansChart.showLoading();

topFansBlogTagChart.setOption(fansBogTagChartOption);
topFansBlogTagChart.showLoading();

readTopAuthorChart.setOption(readTopChartOption);
readTopAuthorChart.on("click", function (param) { 
	topFansBlogTagChart.showLoading();
	var id,subtext;
	//根据点击的是xAsix还是series进行id以及昵称的获取
	if(param.componentType=='series'){
		id=topReadAuthorId[param.dataIndex];
		subtext=param.name;
	}else if(param.componentType=='xAxis'){
		id=topReadAuthorId[param.xAxisIndex];
		subtext=param.value;
	}
	// 发表博客主题的ajax
	$.ajax({
		type : 'get',
		//嵌套ajax同步标志设置为false，避免出现数据未加载情况
		asyn : true,
		//get请求的数据,发送id进行请求构造
		data : {
			id : id
		},
		url : 'getAuthorBlogTag',
		contentType : 'application/json;charset=utf-8',
		dataType : 'json', // 很关键，否则返回的data为字符串
		// 请求的json，设置
		success : function(data) {
			topFansBlogTagChart.hideLoading();
			topFansBlogTagChart.setOption({
				title : {
					//博主昵称的设置
					subtext: subtext
				},
				series : [ {
					data : data.series
				} ]
			});
		}
	});
})
readTopAuthorChart.showLoading();
// 粉丝量表的ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	//get请求的数据
	data : {
		limit : "8"
	},
	url : 'listTopFansAuthor',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		// 全局变量赋值
		topFansId = data.id;
		// 显示图形，并且重新设置Option
		topFansChart.hideLoading();
		topFansChart.setOption({
			xAxis : {
				data : data.authorName
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '粉丝量',
				data : data.fans
			}, {
				// 根据名字对应到相应的系列
				name : '关注量',
				data : data.attention
			}, {
				// 根据名字对应到相应的系列
				name : '阅读量',
				data : data.readSum
			} ]
		});
		// 发表博客主题的ajax
		$.ajax({
			type : 'get',
			//嵌套ajax同步标志设置为false，避免出现数据未加载情况
			asyn : false,
			//get请求的数据,初始为top1博主信息
			data : {
				id : topFansId[0]
			},
			url : 'getAuthorBlogTag',
			contentType : 'application/json;charset=utf-8',
			dataType : 'json', // 很关键，否则返回的data为字符串
			// 请求的json，设置
			success : function(data) {
				topFansBlogTagChart.hideLoading();
				topFansBlogTagChart.setOption({
					title : {
						//获取top1博主昵称
						subtext: topFansChart.getOption().xAxis[0].data[0]
					},
					series : [ {
						data : data.series
					} ]
				});
			}
		});
	}
});
// 创建数量ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	url : 'listYearCreatedNum',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		authorCreatedNumChart.hideLoading();
		authorCreatedNumChart.setOption({
			xAxis : {
				data : data.year
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '创建量',
				data : data.createdNum
			} ]
		});
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
		topReadAuthorId = data.id;
		readTopAuthorChart.hideLoading();
		readTopAuthorChart.setOption({
			xAxis : {
				data : data.authorName
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '粉丝量',
				data : data.fans
			}, {
				// 根据名字对应到相应的系列
				name : '关注量',
				data : data.attention
			}, {
				// 根据名字对应到相应的系列
				name : '阅读量',
				data : data.readSum
			} ]
		});
	}
});