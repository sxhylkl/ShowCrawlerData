package com.ccran.mapper;

import java.util.List;

import com.ccran.pojo.CSDNAuthor;
import com.ccran.pojo.CSDNBlog;

public interface CSDNMapper {
	//列出min-max之间的博客发表数量
	int listYearBlogPublishNum(String min,String max);
	//列出min-max之间的博客阅读量
	int listYearBlogReadNum(String min,String max);
	//列出min-max之间的博客
	List<CSDNBlog> listYearBlogs(String min,String max);
	//列出所有博客
	List<CSDNBlog> listAllBlogs();
	
	//列出前limit名博主信息
	List<CSDNAuthor> listTopRank(int limit);
	//根据Id列出博主所有博客
	List<CSDNBlog> listAuthorBlogById(int id);
}
