package com.ccran.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ccran.service.CSDNService;

@Controller
public class CSDNController {
	@Autowired
	CSDNService csdnService;
	
	@RequestMapping("/getCSDNAllBlogWordCloud")
	public @ResponseBody JSONObject getCSDNAllBlogWordCloud(){
		return csdnService.getAllBlogWordCloud();
	}
	
	@RequestMapping("/getCSDNYearBlogNum")
	public @ResponseBody JSONObject getCSDNYearBlogNum(){
		return csdnService.getYearBlogNum();
	}
	
	@RequestMapping("/getCSDNYearMainTopic")
	public @ResponseBody JSONObject getCSDNYearMainTopic(int year,int topicNum){
		return csdnService.getBlogMainTopicByYearAndTopicNum(year, topicNum);
	}
	
	@RequestMapping("/getCSDNTopRankAuthor")
	public @ResponseBody JSONObject getTopRankAuthor(int limit){
		return csdnService.getTopRankAuthor(limit);
	}
	
	@RequestMapping("/getCSDNAuthorPublishBlogWordCloud")
	public @ResponseBody JSONObject getCSDNAuthorPublishBlogWordCloud(int id){
		return csdnService.getAuthorPublishBlogWordCloud(id);
	}
}
