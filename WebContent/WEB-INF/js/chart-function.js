/**
 * 获取博客年度主题词云的数据
 * year:年份
 * topicNum:共topicNum个主题
 * isAsyn:是否异步请求
 * url:请求的url
 * chart:需要展示的表
 */
function getBlogYearMainTopicChartData(year, topicNum, isAsyn,
		url,chart) {
	$.ajax({
		type : 'get',
		asyn : isAsyn,
		// get请求的数据,发送id进行请求构造
		data : {
			year : year,
			topicNum : topicNum
		},
		url : url,
		contentType : 'application/json;charset=utf-8',
		dataType : 'json', // 很关键，否则返回的data为字符串
		// 请求的json，设置
		success : function(data) {
			chart.hideLoading();
			chart.setOption({
				title : {
					text : year+'年博客年度主题',
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

/**
 * 获取所有博客组成的词云
 * url:请求的url
 * chart:需要进行展示的词云
 */
function getAllBlogWordCloud(url,chart){
	$.ajax({
		type : 'get',
		asyn : true,
		url : url,
		contentType : 'application/json;charset=utf-8',
		dataType : 'json',
		success : function(data) {
			chart.hideLoading();
			chart.setOption({
				title : {
					text : '所有博客关键字'
				},
				series : [ {
					data : data.series
				} ]
			});
		}
	});	
}

/**
 * 获取博主发表博客组成的词云
 * authorid:博主Id
 * authorname:博主名称
 * isAsyn:是否异步
 * url:请求的url
 * chart:需要设置的表格
 */
function getAuthorPublishBlogWordCloudData(authorid, authorname, isAsyn,
		url,chart) {
	$.ajax({
		type : 'get',
		asyn : isAsyn,
		// get请求的数据,发送id进行请求构造
		data : {
			id : authorid
		},
		url : url,
		contentType : 'application/json;charset=utf-8',
		dataType : 'json', // 很关键，否则返回的data为字符串
		// 请求的json，设置
		success : function(data) {
			chart.hideLoading();
			chart.setOption({
				title : {
					//标题设置
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