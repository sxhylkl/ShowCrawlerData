//全局作者Id
var authorId;
// 词云Option
var wordCloudOption = {
	title : {
		text : '',
		x : 'center'
	},
	toolbox : {
		feature : {
			dataView : {
				show : true,
				readOnly : false
			},
			saveAsImage : {
				show : true
			}
		}
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

// 饼图
var pieChartOption = {
	tooltip : {
		trigger : 'item',
		formatter : "{a} <br/>{b} : {c} ({d}%)"
	},
	legend : {
		orient : 'vertical',
		left : 'left',
		data : []
	},
	series : [ {
		name : '主题',
		type : 'pie',
		radius : '55%',
		center : [ '50%', '60%' ],
		data : [],
		itemStyle : {
			emphasis : {
				shadowBlur : 10,
				shadowOffsetX : 0,
				shadowColor : 'rgba(0, 0, 0, 0.5)'
			}
		}
	} ]
};
// 柱状图基本配置
var barGraphOption = {
	title : {
		text : ''
	},
	toolbox : {
		feature : {
			dataView : {
				show : true,
				readOnly : false
			},
			saveAsImage : {
				show : true
			}
		}
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
// 折线图、柱状图混合
var mixedChartOption = {
	tooltip : {
		trigger : 'axis',
		axisPointer : {
			type : 'cross',
			crossStyle : {
				color : '#999'
			}
		}
	},
	toolbox : {
		feature : {
			dataView : {
				show : true,
				readOnly : false
			},
			magicType : {
				show : true,
				type : [ 'line', 'bar' ]
			},
			restore : {
				show : true
			},
			saveAsImage : {
				show : true
			}
		}
	},
	legend : {
		data : [ '发表量', '阅读量' ]
	},
	xAxis : [ {
		type : 'category',
		data : [],
		axisPointer : {
			type : 'shadow'
		},
		axisLabel : {
			interval : 0,
			rotate : 40
		},
		// 开启x轴点击事件
		triggerEvent : true
	} ],
	yAxis : [ {
		type : 'value',
		name : '发表数目'
	}, {
		type : 'value',
		name : '阅读数目',
		axisLabel : {
			// 对刻度数量进行格式化
			formatter : function(value, index) {
				return value / 1000 + 'k'
			}
		}
	} ],
	series : [ {
		name : '发表量',
		type : 'bar',
		data : []
	}, {
		name : '阅读量',
		type : 'line',
		yAxisIndex : 1,
		data : []
	} ]
};

// 所有博客词云
var allBlogWordCloud = echarts.init(document
		.getElementById('all-blog-wordcloud'));
allBlogWordCloud.setOption(wordCloudOption);
allBlogWordCloud.showLoading();
// 所有博客关键字ajax获取
$.ajax({
	type : 'get',
	asyn : true,
	url : 'getCSDNAllBlogWordCloud',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json',
	success : function(data) {
		allBlogWordCloud.hideLoading();
		allBlogWordCloud.setOption({
			title : {
				text : '所有博客关键字'
			},
			series : [ {
				data : data.series
			} ]
		});
	}
});

// 所有年份博客数量表，包括
var yearBlogNumChart = echarts.init(document.getElementById('year-blog-num'));
yearBlogNumChart.setOption(mixedChartOption);
yearBlogNumChart.showLoading();
yearBlogNumChart.on("click", function(param) {
	// 根据点击的是xAsix还是series进行年份的获取
	var year;
	if (param.componentType == 'series') {
		year = param.name;
	} else if (param.componentType == 'xAxis') {
		year = param.value;
	}
	getBlogYearMainTopicChartData(year, 5, true);
})
// 年度博客数据的ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	url : 'getCSDNYearBlogNum',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		yearBlogNumChart.hideLoading();
		yearBlogNumChart.setOption({
			title : {
				text : "博客年度量"
			},
			xAxis : [ {
				data : data.year
			} ],
			series : [ {
				name : '发表量',
				type : 'bar',
				yAsixIndex : 0,
				data : data.blogPublishNum
			}, {
				// 指定yAsix对应的索引，同时类型为折线
				name : '阅读量',
				type : 'line',
				yAsixIndex : 1,
				data : data.blogReadNum
			} ]
		});
	}
});

/**
 * 通过年份、主题数量、是否异步获取博客年度主题表的数据
 */
function getBlogYearMainTopicChartData(year, topicNum, isAsyn) {
	$.ajax({
		type : 'get',
		asyn : isAsyn,
		// get请求的数据,发送id进行请求构造
		data : {
			year : year,
			topicNum : topicNum
		},
		url : 'getCSDNYearMainTopic',
		contentType : 'application/json;charset=utf-8',
		dataType : 'json', // 很关键，否则返回的data为字符串
		// 请求的json，设置
		success : function(data) {
			yearBlogMainTopicChart.hideLoading();
			yearBlogMainTopicChart.setOption({
				title : {
					text : year + '年博客年度主题',
					x : 'center'
				},
				legend : {
					orient : 'vertical',
					x : 'left',
					data : data.legend
				},
				series : [ {
					data : data.series
				} ]
			});
		}
	});
}
var yearBlogMainTopicChart = echarts.init(document
		.getElementById('year-blog-main-topic'));
yearBlogMainTopicChart.setOption(pieChartOption);
yearBlogMainTopicChart.showLoading();
getBlogYearMainTopicChartData(2018, 5, true);

var authorBlogWordCloud = echarts.init(document
		.getElementById('author-blog-wordcloud'));
authorBlogWordCloud.setOption(wordCloudOption);
authorBlogWordCloud.showLoading();

var topRankAuthorChart = echarts.init(document
		.getElementById('top-rank-author'));
topRankAuthorChart.setOption(barGraphOption);
topRankAuthorChart.showLoading();
topRankAuthorChart.on("click", function(param) {
	authorBlogWordCloud.showLoading();
	var authorid, authorname;
	// 根据点击的是xAsix还是series进行id以及昵称的获取
	if (param.componentType == 'series') {
		authorid = authorId[param.dataIndex];
		authorname = param.name;
	} else if (param.componentType == 'xAxis') {
		authorid = authorId[param.xAxisIndex];
		authorname = param.value;
	}
	// 词云加载
	getAuthorPublishBlogWordCloudData(authorid, authorname, true);
})
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
		url : 'getCSDNAuthorPublishBlogWordCloud',
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
// 博主等级ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	// get请求的数据
	data : {
		limit : "8"
	},
	url : 'getCSDNTopRankAuthor',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		// 全局变量赋值
		authorId = data.idList;
		// 隐藏加载动画，对Option数据赋值
		topRankAuthorChart.hideLoading();
		topRankAuthorChart.setOption({
			title : {
				text : '博主排行榜'
			},
			legend : {
				data : [ '访问量', '粉丝数', '喜欢数','评论数', '等级', '原创量','积分数' ],
				// 粉丝量表主要对粉丝量感兴趣，隐藏其他数据
				selected : {
					'粉丝数' : false,
					'喜欢数' : false,
					'评论数' : false,
					'等级' : false,
					'原创量' : false,
					'积分数' : false
				}
			},
			xAxis : {
				data : data.nameList
			},
			series : [ {
				// 根据名字对应到相应的系列
				name : '访问量',
				type : 'bar',
				data : data.visitNumList
			}, {
				// 根据名字对应到相应的系列
				name : '粉丝数',
				type : 'bar',
				data : data.fansNumList
			}, {
				// 根据名字对应到相应的系列
				name : '喜欢数',
				type : 'bar',
				data : data.likeNumList
			},{
				// 根据名字对应到相应的系列
				name : '评论数',
				type : 'bar',
				data : data.commentNumList
			}, {
				// 根据名字对应到相应的系列
				name : '等级',
				type : 'bar',
				data : data.levelNumList
			}, {
				// 根据名字对应到相应的系列
				name : '原创量',
				type : 'bar',
				data : data.blogNumList
			}, {
				// 根据名字对应到相应的系列
				name : '积分数',
				type : 'bar',
				data : data.integralList
			} ]
		});
		// 根据粉丝量top1的博主进行第一次加载，同时使用同步加载，以免出现id没有加载出来的情况
		getAuthorPublishBlogWordCloudData(authorId[0],
				topRankAuthorChart.getOption().xAxis[0].data[0], false);
	}
});