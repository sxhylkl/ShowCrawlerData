/**
 * 表一：所有博客关键字组成词云
 */ 
var allBlogWordCloud = echarts.init(document
		.getElementById('all-blog-wordcloud'));
allBlogWordCloud.setOption(wordCloudOption);
allBlogWordCloud.showLoading();
getAllBlogWordCloud('getCSDNAllBlogWordCloud',allBlogWordCloud);

/**
 * 表二：博客年度发表量以及阅读量
 */ 
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
	//根据选择的年份展示该年份的主题
	getBlogYearMainTopicChartData(year, 5, true
			,'getCSDNYearMainTopic',yearBlogMainTopicChart);
})
// 年度博客数据的ajax请求，包括发表量以及阅读量
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
 * 表三：根据年份展示的年度主题词云
 */ 
var yearBlogMainTopicChart = echarts.init(document
		.getElementById('year-blog-main-topic'));
yearBlogMainTopicChart.setOption(pieChartOption);
yearBlogMainTopicChart.showLoading();
//初始化为2018年度主题
getBlogYearMainTopicChartData(2018, 5, true
		,'getCSDNYearMainTopic',yearBlogMainTopicChart);
/**
 * 表五：根据博主发表博客所组成的词云
 */
var authorBlogWordCloud = echarts.init(document
		.getElementById('author-blog-wordcloud'));
authorBlogWordCloud.setOption(wordCloudOption);
authorBlogWordCloud.showLoading();

/**
 * 表四：根据博主的排名进行排名的博主信息
 */
//全局作者Id
var authorId;
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
	getAuthorPublishBlogWordCloudData(authorid, authorname, true,
			'getCSDNAuthorPublishBlogWordCloud',authorBlogWordCloud);
})
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
				topRankAuthorChart.getOption().xAxis[0].data[0], false,
				'getCSDNAuthorPublishBlogWordCloud',authorBlogWordCloud);
	}
});