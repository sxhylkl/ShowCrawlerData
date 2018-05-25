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

//环形图基本配置
var doughnutChartOption = {
	title : {
		text : '',
		x : 'center'
	},
	tooltip : {
		trigger : 'item',
		formatter : "{a} <br/>{b}: {c} ({d}%)"
	},
	legend : {
		orient : 'vertical',
		x : 'right',
		data : []
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
	series : [ {
		name : '主题',
		type : 'pie',
		radius : [ '50%', '70%' ],
		avoidLabelOverlap : false,
		label : {
			normal : {
				show : false,
				position : 'center'
			},
			emphasis : {
				show : true,
				textStyle : {
					fontSize : '30',
					fontWeight : 'bold'
				}
			}
		},
		labelLine : {
			normal : {
				show : false
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

// 折线图基本配置
var lineChartOption = {
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