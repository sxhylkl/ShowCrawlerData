﻿package com.ccran.mapper;

import java.util.List;

import com.ccran.pojo.CnblogAuthor;
import com.ccran.pojo.CnblogBlog;

/**
 * 
 * @author chenran
 * CnblogAuthor操作
 */
public interface CnblogMapper {
	//粉丝量最多的前limit个
	public List<CnblogAuthor> listTopFansAuthor(int limit);
	//阅读量最多的前limit个
	public List<CnblogAuthor> listTopReadAuthor(int limit);
	//查询年份min-max年份之间的数量
	public int listYearAuthorCreatedNum(String min,String max);
	//根据id获取CnblogBlog列表
	public List<CnblogBlog> listAllBlogByAuthorId(int id);
}
