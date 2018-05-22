package com.ccran.service;

import com.alibaba.fastjson.JSONObject;

public interface CnblogService {
	/**
	 * 查询粉丝量前limit的博主，并且封装成json返回
	 * @param limit
	 * @return
	 */
	public JSONObject getTopFansAuthorJson(int limit);
	
	/**
	 * 查询阅读量前limit的博主
	 * @param limit
	 * @return
	 */
	public JSONObject getTopReadAuthorJson(int limit);
	
	/**
	 * 通过id获取作者博客关键字信息
	 * @param id
	 * @return
	 */
	public JSONObject getAuthorBlogKeywordById(int id);
	
	/**
	 * 获取所有博客关键字信息
	 * @return
	 */
	public JSONObject getAllBlogKeyword();
	
	/**
	 * 获取年份数据，如博客年创建量，博文年发表量，博文年阅读量
	 * @return
	 */
	public JSONObject getYearData();
	
	/**
	 * 根据年份以及所需要的主题数量获取数据
	 * @return
	 */
	public JSONObject getBlogMainTopicByYearAndNum(int year,int topicNum);
}
