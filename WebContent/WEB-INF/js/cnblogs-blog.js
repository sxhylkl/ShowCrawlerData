//博客创建表
var blogCreatedChart = echarts
		.init(document.getElementById('blog-created-num'));
var blogCreatedOption = {
	title : {
		text : "博客年度创建量"
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
blogCreatedChart.setOption(blogCreatedOption);
blogCreatedChart.showLoading();
$.ajax({
	type : 'get',
	asyn : true,
	url : 'listYearBlogCreatedNum',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		blogCreatedChart.hideLoading();
		blogCreatedChart.setOption({
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
// 所有博客的词云展示
var allBlogWordCloudChart = echarts.init(document.getElementById('all-blog-wordcloud'));
var allBlogWordCloudOption = {
	title : {
		text : '博客主题',
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
allBlogWordCloudChart.setOption(allBlogWordCloudOption);
allBlogWordCloudChart.showLoading();
$.ajax({
	type : 'get',
	asyn : true,
	url : 'getAllBlogWordCloud',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json',
	success : function(data) {
		allBlogWordCloudChart.hideLoading();
		allBlogWordCloudChart.setOption({
			series : [ {
				data : data.series
			} ]
		});
	}
});
