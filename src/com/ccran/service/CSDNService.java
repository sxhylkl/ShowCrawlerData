package com.ccran.service;

import com.alibaba.fastjson.JSONObject;

public interface CSDNService {
	//获取所有博客词云
	public JSONObject getAllBlogWordCloud();
	
	//获取博文所有年份数量
	public JSONObject getYearBlogNum();
	
	//根据年份获取年度词云
	public JSONObject getBlogMainTopicByYearAndTopicNum(int year,int topicNum);
	
	//获取排行前limit的博主信息
	public JSONObject getTopRankAuthor(int limit);
	
	//根据Id获取该博主发表博客的词云信息
	public JSONObject getAuthorPublishBlogWordCloud(int id);
}
