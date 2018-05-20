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
	 * 获取04年到当前年份的每年博主创建量，并且封装成json返回
	 * @return
	 */
	public JSONObject getYearCreatedAuthorNum();
	
	/**
	 * 通过id获取作者博客关键字信息
	 * @param id
	 * @return
	 */
	public JSONObject getAuthorBlogKeywordById(int id);
	
	/**
	 * 获取04年到当前年份的每年博客创建量，并且封装成json返回
	 * @return
	 */
	public JSONObject getYearCreatedBlogNum();
	
	/**
	 * 获取所有博客关键字信息
	 * @return
	 */
	public JSONObject getAllBlogKeyword();
}
