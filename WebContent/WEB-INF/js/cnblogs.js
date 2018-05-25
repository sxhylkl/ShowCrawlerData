/**
 * 表七：年度最热主题环形图,数据生成依赖于博客年度图
 */ 
var blogYearMainTopicChart = echarts.init(document
		.getElementById('blog-year-main-topic'));
blogYearMainTopicChart.setOption(doughnutChartOption);
blogYearMainTopicChart.showLoading();
// 初始化为2018年数据
getBlogYearMainTopicChartData(2018,5,true,
		'getBlogYearMainTopic',blogYearMainTopicChart);

/**
 * 表一：博主的年度创建量
 */ 
var authorYearCreatedNumChart = echarts.init(document
		.getElementById('author-year-created-num'));
authorYearCreatedNumChart.setOption(lineChartOption);
authorYearCreatedNumChart.showLoading();

/**
 * 表六：博客年度量，包括发表量以及阅读量
 */
var blogYearNumChart = echarts.init(document.getElementById('blog-year-num'));
blogYearNumChart.setOption(mixedChartOption);
blogYearNumChart.showLoading();
blogYearNumChart.on("click", function(param) {
	// 根据点击的是xAsix还是series进行年份的获取
	var year;
	if (param.componentType == 'series') {
		year=param.name;
	} else if (param.componentType == 'xAxis') {
		year=param.value;
	}
	//根据点击年份获取年度主题
	getBlogYearMainTopicChartData(year,5,true,
			'getBlogYearMainTopic',blogYearMainTopicChart);
})

// 各种年度数据的ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	url : 'listYearData',
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
				data : data.authorCreatedNum
			} ]
		});
		blogYearNumChart.hideLoading();
		blogYearNumChart.setOption({
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
 * 表四：博主所写博客的关键字词云 
 * 词云数据生成依赖于博主Id
 */
var authorBlogWordCloud = echarts.init(document
		.getElementById('author-blog-wordcloud'));
authorBlogWordCloud.setOption(wordCloudOption);
authorBlogWordCloud.showLoading();

/**
 * 表二：
 * 1.通过DOM初始化博主粉丝量排行榜 
 * 2.设定图表选项为barGraphOption，即柱状图 
 * 3.设定xAsix以及series的点击事件方法体
 * 4.方法体对词云数据进行AJAX加载
 */
//全局博主ID
var topFansAuthorId, topReadNumAuthorId;
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
	getAuthorPublishBlogWordCloudData(authorid, authorname, true,
			'getAuthorBlogKeyword',authorBlogWordCloud);
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
			title : {
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
				topFansAuthorChart.getOption().xAxis[0].data[0], false,
				'getAuthorBlogKeyword',authorBlogWordCloud);
	}
});

/**
 * 表三：博客阅读量最多的博主排行榜表 
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
	getAuthorPublishBlogWordCloudData(id, name, true,
			'getAuthorBlogKeyword',authorBlogWordCloud);
})
// 博主阅读量表ajax请求
$.ajax({
	type : 'get',
	asyn : true,
	data : {
		limit : "8"
	},
	url : 'listTopReadNumAuthor',
	contentType : 'application/json;charset=utf-8',
	dataType : 'json', // 很关键，否则返回的data为字符串
	// 请求的json，设置
	success : function(data) {
		topReadNumAuthorId = data.id;
		topReadNumAuthorChart.hideLoading();
		topReadNumAuthorChart.setOption({
			title : {
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

/**
 * 表五：所有博客关键字词云
 */ 
var allBlogWordCloud = echarts.init(document
		.getElementById('all-blog-wordcloud'));
allBlogWordCloud.setOption(wordCloudOption);
allBlogWordCloud.showLoading();
getAllBlogWordCloud('getAllBlogWordCloud',allBlogWordCloud);