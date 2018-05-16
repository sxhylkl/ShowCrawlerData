package com.ccran.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccran.pojo.CnblogAuthor;
import com.ccran.service.CnblogAuthorService;

@Controller
public class CnblogAuthorController {
	@Autowired
	CnblogAuthorService cnblogAuthorService;
	
	@RequestMapping("/listTopFans")
	public @ResponseBody JSONObject listTopFans(){
		//Ä¬ÈÏÎª5
		List<CnblogAuthor> authorList=cnblogAuthorService.listTopFans(5);
		List<String> nameList=new ArrayList<String>();
		List<Integer> fansList=new ArrayList<Integer>();
		for(CnblogAuthor author:authorList){
			nameList.add(author.getName());
			fansList.add(author.getFans());
		}
		JSONArray nameArr=JSONArray.parseArray(JSON.toJSONString(nameList));
		JSONArray fansArr=JSONArray.parseArray(JSON.toJSONString(fansList));
		JSONObject jo=new JSONObject();
		jo.put("authorName", nameArr);
		jo.put("fans", fansArr);
		return jo;
	}
	
	@RequestMapping("/showCnblogAuthor")
	public String showCnblogAuthor(){
		return "cnblogAuthor";
	}
}
