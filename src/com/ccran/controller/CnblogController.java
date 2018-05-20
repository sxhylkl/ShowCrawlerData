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
	@RequestMapping("/listTopReadAuthor")
	public @ResponseBody JSONObject getTopReadAuthorJson(int limit) {
		return cnblogAuthorService.getTopReadAuthorJson(limit);
	}

	// 获取每年创建的博主数量
	@RequestMapping("/listYearAuthorCreatedNum")
	public @ResponseBody JSONObject getYearCreatedAuthorNum() {
		return cnblogAuthorService.getYearCreatedAuthorNum();
	}

	// 获取作者id的博文主题
	@RequestMapping("/getAuthorBlogTag")
	public @ResponseBody JSONObject getAuthorBlogTag(int id) {
		return cnblogAuthorService.getAuthorBlogKeywordById(id);
	}

	// 获取每年创建的博客数量
	@RequestMapping("/listYearBlogCreatedNum")
	public @ResponseBody JSONObject getYearCreatedBlogNum() {
		return cnblogAuthorService.getYearCreatedBlogNum();
	}

	// 获取所有博客组成的词云
	@RequestMapping("/getAllBlogWordCloud")
	public @ResponseBody JSONObject getAllBlogWordCloud() {
		return cnblogAuthorService.getAllBlogKeyword();
	}
}
