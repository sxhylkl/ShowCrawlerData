package com.ccran.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccran.mapper.CnblogMapper;
import com.ccran.pojo.CnblogAuthor;

@Service
public class CnblogServiceImp implements CnblogService {
	@Autowired
	CnblogMapper cnblogAuthorMapper;

	@Override
	public JSONObject getTopFansAuthorJson(int limit) {
		// mapper查询数据
		List<CnblogAuthor> authorList = cnblogAuthorMapper.listTopFansAuthor(limit);
		// fastjson封装成JSONObject返回
		List<String> nameList = new ArrayList<String>();
		List<Integer> fansList = new ArrayList<Integer>();
		List<Integer> attentionList = new ArrayList<Integer>();
		List<Integer> readSumList = new ArrayList<Integer>();
		for (CnblogAuthor author : authorList) {
			nameList.add(author.getName());
			fansList.add(author.getFans());
			attentionList.add(author.getAttention());
			readSumList.add(author.getReadSum());
		}
		JSONArray nameArr = JSONArray.parseArray(JSON.toJSONString(nameList));
		JSONArray fansArr = JSONArray.parseArray(JSON.toJSONString(fansList));
		JSONArray attentionArr = JSONArray.parseArray(JSON.toJSONString(attentionList));
		JSONArray readSumArr = JSONArray.parseArray(JSON.toJSONString(readSumList));
		JSONObject jo = new JSONObject();
		jo.put("authorName", nameArr);
		jo.put("fans", fansArr);
		jo.put("attention", attentionArr);
		jo.put("readSum", readSumArr);
		return jo;
	}

	@Override
	public JSONObject getYearCreatedAuthorNum() {
		// 获取年份
		int minYear = 2004, maxYear = Calendar.getInstance().get(Calendar.YEAR) + 1;
		// 逐年获取数量进行封装
		List<String> yearStrList = new ArrayList<String>();
		List<Integer> createdNumList = new ArrayList<Integer>();
		for (int i = minYear; i < maxYear; i++) {
			// 年份添加
			String now = String.valueOf(i), next = String.valueOf(i + 1);
			yearStrList.add(now);
			// 数量添加
			int num = cnblogAuthorMapper.listYearAuthorCreatedNum(now, next);
			createdNumList.add(num);
		}
		// 封装成json返回
		JSONArray yearArr = JSONArray.parseArray(JSON.toJSONString(yearStrList));
		JSONArray numArr = JSONArray.parseArray(JSON.toJSONString(createdNumList));
		JSONObject jo = new JSONObject();
		jo.put("year", yearArr);
		jo.put("createdNum", numArr);
		return jo;
	}

	@Override
	public JSONObject getTopReadAuthorJson(int limit) {
		// mapper查询数据
		List<CnblogAuthor> authorList = cnblogAuthorMapper.listTopReadAuthor(limit);
		// fastjson封装成JSONObject返回
		List<String> nameList = new ArrayList<String>();
		List<Integer> fansList = new ArrayList<Integer>();
		List<Integer> attentionList = new ArrayList<Integer>();
		List<Integer> readSumList = new ArrayList<Integer>();
		for (CnblogAuthor author : authorList) {
			nameList.add(author.getName());
			fansList.add(author.getFans());
			attentionList.add(author.getAttention());
			readSumList.add(author.getReadSum());
		}
		JSONArray nameArr = JSONArray.parseArray(JSON.toJSONString(nameList));
		JSONArray fansArr = JSONArray.parseArray(JSON.toJSONString(fansList));
		JSONArray attentionArr = JSONArray.parseArray(JSON.toJSONString(attentionList));
		JSONArray readSumArr = JSONArray.parseArray(JSON.toJSONString(readSumList));
		JSONObject jo = new JSONObject();
		jo.put("authorName", nameArr);
		jo.put("fans", fansArr);
		jo.put("attention", attentionArr);
		jo.put("readSum", readSumArr);
		return jo;
	}
}
