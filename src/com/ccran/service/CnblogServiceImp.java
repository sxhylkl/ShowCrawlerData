package com.ccran.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccran.mapper.CnblogMapper;
import com.ccran.pojo.CnblogAuthor;
import com.ccran.pojo.CnblogBlog;
import com.ccran.pojo.NameValuePojo;

@Service
public class CnblogServiceImp implements CnblogService {
	@Autowired
	CnblogMapper cnblogAuthorMapper;

	/**
	 * 通过提供作者列表返回封装好的json对象
	 * 
	 * @param authorList
	 * @return
	 */
	private JSONObject getByCnblogAuthorList(List<CnblogAuthor> authorList) {
		List<String> nameList = new ArrayList<String>();
		List<Integer> fansList = new ArrayList<Integer>();
		List<Integer> attentionList = new ArrayList<Integer>();
		List<Integer> readSumList = new ArrayList<Integer>();
		List<Integer> idList = new ArrayList<Integer>();
		for (CnblogAuthor author : authorList) {
			nameList.add(author.getName());
			fansList.add(author.getFans());
			attentionList.add(author.getAttention());
			readSumList.add(author.getReadSum());
			idList.add(author.getId());
		}
		JSONArray idArr = JSONArray.parseArray(JSON.toJSONString(idList));
		JSONArray nameArr = JSONArray.parseArray(JSON.toJSONString(nameList));
		JSONArray fansArr = JSONArray.parseArray(JSON.toJSONString(fansList));
		JSONArray attentionArr = JSONArray.parseArray(JSON.toJSONString(attentionList));
		JSONArray readSumArr = JSONArray.parseArray(JSON.toJSONString(readSumList));
		JSONObject jo = new JSONObject();
		jo.put("id", idArr);
		jo.put("authorName", nameArr);
		jo.put("fans", fansArr);
		jo.put("attention", attentionArr);
		jo.put("readSum", readSumArr);
		return jo;
	}
	
	/**
	 * 通过博客列表获取关键字信息，一般用以作为前端词云数据
	 * @return
	 */
	private JSONArray getKeywordByBlogList(List<CnblogBlog> blogList){
		//创建关注的词性
		Set<String> attentionNature=new HashSet<String>(){{
			add("en");add("n");
		}};
		StringBuilder sb = new StringBuilder();
		// 构造成句子
		for (CnblogBlog blog : blogList) {
			if (blog.getType() != null && !blog.getType().isEmpty()) {
				sb.append(blog.getType() + ',');
			} else if (blog.getTag() != null && !blog.getTag().isEmpty()) {
				sb.append(blog.getTag() + ',');
			} else {
				sb.append(blog.getTitle() + ',');
			}
		}
		// 通过ansj分词,并且通过HashMap进行词频统计
		Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
		Result result = ToAnalysis.parse(sb.toString());
		for (Term term : result.getTerms()) {
			// 词性在关注词性的集合中进行累加计数
			if (attentionNature.contains(term.getNatureStr())) {
				String word = term.getName();
				int times = wordCountMap.getOrDefault(word, 0);
				wordCountMap.put(word, ++times);
			}
		}
		// 遍历map进行添加
		List<NameValuePojo> nameValueList = new ArrayList<NameValuePojo>();
		Iterator iterator = wordCountMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next();
			nameValueList.add(new NameValuePojo(entry.getValue(), entry.getKey()));
		}
		return JSONArray.parseArray(JSON.toJSONString(nameValueList));
	}

	@Override
	public JSONObject getTopFansAuthorJson(int limit) {
		// mapper查询数据
		List<CnblogAuthor> authorList = cnblogAuthorMapper.listTopFansAuthor(limit);
		return getByCnblogAuthorList(authorList);
	}

	@Override
	public JSONObject getTopReadAuthorJson(int limit) {
		// mapper查询数据
		List<CnblogAuthor> authorList = cnblogAuthorMapper.listTopReadAuthor(limit);
		return getByCnblogAuthorList(authorList);
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
	public JSONObject getAuthorBlogKeywordById(int id) {
		// 根据Id获取博文列表
		List<CnblogBlog> blogList = cnblogAuthorMapper.listAllBlogByAuthorId(id);
		//获取关键字列表并返回
		JSONArray keyWordArr=getKeywordByBlogList(blogList);
		JSONObject res = new JSONObject();
		res.put("series", keyWordArr);
		return res;
	}

	@Override
	public JSONObject getYearCreatedBlogNum() {
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
			int num = cnblogAuthorMapper.listYearBlogCreatedNum(now, next);
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
	public JSONObject getAllBlogKeyword() {
		List<CnblogBlog> allBlogList=cnblogAuthorMapper.listAllBlog();
		//获取关键字列表并返回
		JSONArray keyWordArr=getKeywordByBlogList(allBlogList);
		JSONObject res = new JSONObject();
		res.put("series", keyWordArr);
		return res;
	}
}
