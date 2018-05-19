// 基于准备好的dom，初始化echarts实例
var topFansChart = echarts.init(document.getElementById('top-fans-author'));
var authorCreatedNumChart = echarts.init(document
		.getElementById('author-created-num'));
var readTopAuthorChart=echarts.init(document.getElementById('read-top-author'));
// 粉丝量排行图表配置
var fansChartOption = {
	title : {
		text : "博主知名排行"
	},
	tooltip : {},
	legend : {
		data : [ '粉丝量','关注量','阅读量' ],
		selected:{
			'关注量':false,
			'阅读量':false
		}
	},
	xAxis : {
		data : [],
		axisLabel : {
			interval : 0,
			rotate : 40
		}
	},
	yAxis : {},
	series : [ {
		name : '粉丝量',
		type : 'bar',
		data : []
	},{
		name : '关注量',
		type : 'bar',
		data : []
	},{
		name : '阅读量',
		type : 'bar',
		data : []
	} ]
};
// 阅读量排行图表配置
var readTopChartOption = {
		title : {
			text : "博主文章阅读排行"
		},
		tooltip : {},
		legend : {
			data : [ '阅读量','关注量','粉丝量' ],
			selected:{
				'关注量':false,
				'粉丝量':false
			}
		},
		xAxis : {
			data : [],
			axisLabel : {
				interval : 0,
				rotate : 40
			}
		},
		yAxis : {},
		series : [ {
			name : '阅读量',
			type : 'bar',
			data : []
		},{
			name : '关注量',
			type : 'bar',
			data : []
		},{
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
// 使用刚指定的配置项和数据显示图表
topFansChart.setOption(fansChartOption);
authorCreatedNumChart.setOption(createNumOption);
readTopAuthorChart.setOption(readTopChartOption);
//粉丝量表的ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	data : {
		limit : "8"
	},
	url : 'listTopFansAuthor',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', //很关键，否则返回的data为字符串
	//请求的json，设置
	success : function(data) {
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
	}
});
//创建数量ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	url : 'listYearCreatedNum',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', //很关键，否则返回的data为字符串
	//请求的json，设置
	success : function(data) {
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
//博主阅读量表ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	data : {
		limit : "8"
	},
	url : 'listTopReadAuthor',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', //很关键，否则返回的data为字符串
	//请求的json，设置
	success : function(data) {
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