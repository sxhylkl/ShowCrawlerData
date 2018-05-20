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
	private static final int MIN_YEAR = 2004;
	private static final int MAX_YEAR = Calendar.getInstance().get(Calendar.YEAR) + 1;

	@Autowired
	CnblogMapper cnblogAuthorMapper;

	/**
	 * 通过提供作者列表返回封装好的封装json对象
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
	 * 通过博文列表创建词频Map，词-词频（此处词频只对博客的关键词出现次数进行统计而不涉及博客的阅读量）
	 * 
	 * @param blogList
	 * @return
	 */
	private Map<String, Integer> getWordCountMapByBlogList(List<CnblogBlog> blogList) {
		// 创建关注的词性
		Set<String> attentionNature = new HashSet<String>() {
			{
				add("en");
				add("n");
			}
		};
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
		Result result = ToAnalysis.parse(sb.toString());
		Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
		for (Term term : result.getTerms()) {
			// 词性在关注词性的集合中进行累加计数
			if (attentionNature.contains(term.getNatureStr())) {
				String word = term.getName();
				int times = wordCountMap.getOrDefault(word, 0);
				wordCountMap.put(word, ++times);
			}
		}
		return wordCountMap;
	}

	/**
	 * 通过博客列表以及博客列表中的总数进行关键字Map的生成
	 * 
	 * @param blogList
	 * @param readSumNum
	 * @return
	 */
	private Map<String, Integer> getWordCountMapByBlogListWithReadNum(List<CnblogBlog> blogList) {
		// 创建关注的词性
		Set<String> attentionNature = new HashSet<String>() {
			{
				add("en");
				add("n");
			}
		};
		//遍历并进行统计
		Map<String,Integer> wordCountMap=new HashMap<String,Integer>();
		//分词并统计O(n2)复杂度
		for(CnblogBlog blog:blogList){
			//获取标题以及阅读量
			String title=blog.getTitle();
			int readNum=blog.getReadNum();
			//保证阅读量大于0
			readNum+=1;
			if(title!=null){
				Result result=ToAnalysis.parse(title);
				for(Term term:result.getTerms()){
					if(attentionNature.contains(term.getNatureStr())){
						String word = term.getName();
						int times = wordCountMap.getOrDefault(word, 0);
						//通过阅读量进行比重的配置
						wordCountMap.put(word, times+readNum);
					}
				}
			}
		}
		return wordCountMap;
	}

	/**
	 * 通过词云Map结构（词-频率）封装成JSONObject返回
	 * 
	 * @return
	 */
	private JSONObject getKeywordByMap(Map<String, Integer> wordCountMap) {
		// 遍历map进行添加
		List<NameValuePojo> nameValueList = new ArrayList<NameValuePojo>();
		Iterator iterator = wordCountMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next();
			nameValueList.add(new NameValuePojo(entry.getValue(), entry.getKey()));
		}
		JSONArray keyWordArr = JSONArray.parseArray(JSON.toJSONString(nameValueList));
		JSONObject res = new JSONObject();
		res.put("series", keyWordArr);
		return res;
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

	/**
	 * 每年创建作者数量数据构造
	 */
	@Override
	public JSONObject getYearCreatedAuthorNum() {
		// 逐年获取数量进行封装
		List<String> yearStrList = new ArrayList<String>();
		List<Integer> createdNumList = new ArrayList<Integer>();
		for (int i = MIN_YEAR; i < MAX_YEAR; i++) {
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
		jo.put("num", numArr);
		return jo;
	}

	/**
	 * 作者词云数据的构造
	 */
	@Override
	public JSONObject getAuthorBlogKeywordById(int id) {
		// 根据Id获取博文列表
		List<CnblogBlog> blogList = cnblogAuthorMapper.listAllBlogByAuthorId(id);
		// 生成词频Map
		Map<String, Integer> wordCountMap = getWordCountMapByBlogList(blogList);
		return getKeywordByMap(wordCountMap);
	}

	/**
	 * 每年创建博文数据构造
	 */
	@Override
	public JSONObject getYearCreatedBlogNum() {
		// 逐年获取数量进行封装
		List<String> yearStrList = new ArrayList<String>();
		List<Integer> createdNumList = new ArrayList<Integer>();
		for (int i = MIN_YEAR; i < MAX_YEAR; i++) {
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
		jo.put("num", numArr);
		return jo;
	}

	/**
	 * 所有博文词云数据构造
	 */
	@Override
	public JSONObject getAllBlogKeyword() {
		// 获取所有博客以及所有博客阅读量
		List<CnblogBlog> allBlogList = cnblogAuthorMapper.listAllBlog();
		// 生成词频Map
		Map<String, Integer> wordCountMap = getWordCountMapByBlogListWithReadNum(allBlogList);
		return getKeywordByMap(wordCountMap);
	}
}
