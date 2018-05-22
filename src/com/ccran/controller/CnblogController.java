package com.ccran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ccran.service.CnblogService;

@Controller
public class CnblogController {
	@Autowired
	CnblogService cnblogAuthorService;

	// 根据数量获取前limit关注量博主的json信息
	@RequestMapping("/listTopFansAuthor")
	public @ResponseBody JSONObject getTopFansAuthorJson(int limit) {
		return cnblogAuthorService.getTopFansAuthorJson(limit);
	}

	// 根据数量获取前limit阅读量博主的json信息
	@RequestMapping("/listTopReadNumAuthor")
	public @ResponseBody JSONObject getTopReadAuthorJson(int limit) {
		return cnblogAuthorService.getTopReadAuthorJson(limit);
	}

	// 获取作者id的博文主题
	@RequestMapping("/getAuthorBlogKeyword")
	public @ResponseBody JSONObject getAuthorBlogKeyword(int id) {
		return cnblogAuthorService.getAuthorBlogKeywordById(id);
	}

	// 获取所有博客组成的词云
	@RequestMapping("/getAllBlogWordCloud")
	public @ResponseBody JSONObject getAllBlogWordCloud() {
		return cnblogAuthorService.getAllBlogKeyword();
	}

	// 获取由年份组成的数据
	@RequestMapping("/listYearData")
	public @ResponseBody JSONObject getYearData() {
		return cnblogAuthorService.getYearData();
	}
	
	//根据年份以及主题数量，获取年份主题
	@RequestMapping("/getBlogYearMainTopic")
	public @ResponseBody JSONObject getBlogYearMainTopic(int year,int topicNum) {
		return cnblogAuthorService.getBlogMainTopicByYearAndNum(year,topicNum);
	}
}
