package com.ccran.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
	@Autowired
	CommonService commonService;
	
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
		// 遍历并进行统计
		Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
		// 分词并统计O(n2)复杂度
		for (CnblogBlog blog : blogList) {
			// 获取标题以及阅读量
			String title = blog.getTitle();
			int readNum = blog.getReadNum();
			// 保证阅读量大于0
			readNum += 1;
			if (title != null) {
				Result result = ToAnalysis.parse(title);
				for (Term term : result.getTerms()) {
					if (attentionNature.contains(term.getNatureStr())) {
						String word = term.getName();
						int times = wordCountMap.getOrDefault(word, 0);
						// 通过阅读量进行比重的配置
						wordCountMap.put(word, times + readNum);
					}
				}
			}
		}
		return wordCountMap;
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
	 * 作者词云数据的构造
	 */
	@Override
	public JSONObject getAuthorBlogKeywordById(int id) {
		// 根据Id获取博文列表
		List<CnblogBlog> blogList = cnblogAuthorMapper.listAllBlogByAuthorId(id);
		// 生成词频Map
		Map<String, Integer> wordCountMap = getWordCountMapByBlogList(blogList);
		return commonService.getKeywordByWordCountMap(wordCountMap);
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
		return commonService.getKeywordByWordCountMap(wordCountMap);
	}

	/**
	 * 年份数据，博主年创建量，博客年发表量，博客年阅读量
	 */
	@Override
	public JSONObject getYearData() {
		// 逐年获取数量进行封装
		List<String> yearStrList = new ArrayList<String>();
		List<Integer> authorCreatedNumList = new ArrayList<Integer>();
		List<Integer> blogPublishNumList = new ArrayList<Integer>();
		List<Integer> blogReadNumList = new ArrayList<Integer>();
		for (int i = MIN_YEAR; i < MAX_YEAR; i++) {
			// 年份添加
			String now = String.valueOf(i), next = String.valueOf(i + 1);
			yearStrList.add(now);
			// 博主年创建数量添加
			int num = cnblogAuthorMapper.listYearAuthorCreatedNum(now, next);
			authorCreatedNumList.add(num);
			// 博客年发表数量添加
			num = cnblogAuthorMapper.listYearBlogCreatedNum(now, next);
			blogPublishNumList.add(num);
			// 博客年阅读量添加
			num = cnblogAuthorMapper.listYearReadNum(now, next);
			blogReadNumList.add(num);
		}
		// 封装成json返回
		JSONArray yearArr = JSONArray.parseArray(JSON.toJSONString(yearStrList));
		JSONArray authorCreatedNumArr = JSONArray.parseArray(JSON.toJSONString(authorCreatedNumList));
		JSONArray blogPublishNumArr = JSONArray.parseArray(JSON.toJSONString(blogPublishNumList));
		JSONArray blogReadNumArr = JSONArray.parseArray(JSON.toJSONString(blogReadNumList));
		JSONObject jo = new JSONObject();
		jo.put("year", yearArr);
		jo.put("authorCreatedNum", authorCreatedNumArr);
		jo.put("blogPublishNum", blogPublishNumArr);
		jo.put("blogReadNum", blogReadNumArr);
		return jo;
	}
	
	@Override
	public JSONObject getBlogMainTopicByYearAndNum(int year, int topicNum) {
		//首先获取year-year+1之间所有博客
		List<CnblogBlog> blogList=cnblogAuthorMapper
				.listBlogByYear(String.valueOf(year), String.valueOf(year+1));
		// 生成词频Map
		Map<String, Integer> wordCountMap = getWordCountMapByBlogListWithReadNum(blogList);
		List<Map.Entry<String, Integer>> res = commonService.getListOrderByMapValue(wordCountMap);
		//获取排序结果,根据数量进行json生成并返回
		List<String> legendList=new ArrayList<String>(topicNum);
		List<NameValuePojo> seriesList=new ArrayList<NameValuePojo>(topicNum);
		for(int i=0;i<topicNum && i<res.size();i++){
			legendList.add(res.get(i).getKey());
			seriesList.add(new NameValuePojo(res.get(i).getValue(),res.get(i).getKey()));
		}
		if(legendList.isEmpty()){
			legendList.add("无");
			seriesList.add(new NameValuePojo(0,"无"));
		}
		JSONArray legendArr=JSONArray.parseArray(JSON.toJSONString(legendList));
		JSONArray seriesArr=JSONArray.parseArray(JSON.toJSONString(seriesList));
		JSONObject jo=new JSONObject();
		jo.put("legend", legendArr);
		jo.put("series", seriesArr);
		return jo;
	}
}
