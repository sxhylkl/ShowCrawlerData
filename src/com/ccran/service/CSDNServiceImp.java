package com.ccran.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccran.mapper.CSDNMapper;
import com.ccran.pojo.CSDNAuthor;
import com.ccran.pojo.CSDNBlog;
import com.ccran.pojo.NameValuePojo;

@Service
public class CSDNServiceImp implements CSDNService {
	private static final int MIN_YEAR = 2000;
	private static final int MAX_YEAR = Calendar.getInstance().get(Calendar.YEAR) + 1;;

	@Autowired
	CSDNMapper csdnMapper;
	@Autowired
	CommonService commonService;

	/**
	 * 通过博文列表创建词频Map，词-词频（此处词频只对博客的关键词出现次数进行统计而不涉及博客的阅读量）
	 * 
	 * @param blogList
	 * @return
	 */
	private Map<String, Integer> getWordCountMapByBlogList(List<CSDNBlog> blogList) {
		// 创建关注的词性
		Set<String> attentionNature = new HashSet<String>() {
			{
				add("en");
				add("n");
			}
		};
		StringBuilder sb = new StringBuilder();
		// 构造成句子
		for (CSDNBlog blog : blogList) {
			if (blog.getTag() != null && !blog.getTag().isEmpty()) {
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
	private Map<String, Integer> getWordCountMapByBlogListWithReadNum(List<CSDNBlog> blogList) {
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
		for (CSDNBlog blog : blogList) {
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

	/**
	 * 通过提供作者列表返回封装好的封装json对象
	 * 
	 * @param authorList
	 * @return
	 */
	private JSONObject getByCSDNAuthorList(List<CSDNAuthor> authorList) {
		List<Integer> idList = new ArrayList<Integer>();
		List<String> nameList = new ArrayList<String>();
		List<Integer> blogNumList = new ArrayList<Integer>();
		List<Integer> fansNumList = new ArrayList<Integer>();
		List<Integer> likeNumList = new ArrayList<Integer>();
		List<Integer> commentNumList = new ArrayList<Integer>();
		List<Integer> levelNumList = new ArrayList<Integer>();
		List<Integer> visitNumList = new ArrayList<Integer>();
		List<Integer> integralList = new ArrayList<Integer>();
		for (CSDNAuthor author : authorList) {
			idList.add(author.getAuthorId());
			nameList.add(author.getAuthorName());
			blogNumList.add(author.getBlogNum());
			fansNumList.add(author.getFansNum());
			likeNumList.add(author.getLikeNum());
			commentNumList.add(author.getCommentNum());
			levelNumList.add(author.getLevelNum());
			visitNumList.add(author.getVisitNum());
			integralList.add(author.getIntegral());
		}
		JSONObject jo = new JSONObject();
		jo.put("idList", JSONArray.parseArray(JSON.toJSONString(idList)));
		jo.put("nameList", JSONArray.parseArray(JSON.toJSONString(nameList)));
		jo.put("blogNumList", JSONArray.parseArray(JSON.toJSONString(blogNumList)));
		jo.put("fansNumList", JSONArray.parseArray(JSON.toJSONString(fansNumList)));
		jo.put("likeNumList", JSONArray.parseArray(JSON.toJSONString(likeNumList)));
		jo.put("commentNumList", JSONArray.parseArray(JSON.toJSONString(commentNumList)));
		jo.put("levelNumList", JSONArray.parseArray(JSON.toJSONString(levelNumList)));
		jo.put("visitNumList", JSONArray.parseArray(JSON.toJSONString(visitNumList)));
		jo.put("integralList", JSONArray.parseArray(JSON.toJSONString(integralList)));
		return jo;
	}

	@Override
	public JSONObject getAllBlogWordCloud() {
		// 获取所有博客以及所有博客阅读量
		List<CSDNBlog> allBlogList = csdnMapper.listAllBlogs();
		// 生成词频Map
		Map<String, Integer> wordCountMap = getWordCountMapByBlogListWithReadNum(allBlogList);
		return commonService.getKeywordByWordCountMap(wordCountMap);
	}

	@Override
	public JSONObject getBlogMainTopicByYearAndTopicNum(int year, int topicNum) {
		// 首先获取year-year+1之间所有博客
		List<CSDNBlog> blogList = csdnMapper.listYearBlogs(String.valueOf(year), String.valueOf(year + 1));
		// 生成词频Map
		Map<String, Integer> wordCountMap = getWordCountMapByBlogListWithReadNum(blogList);
		List<Map.Entry<String, Integer>> res = commonService.getListOrderByMapValue(wordCountMap);
		// 获取排序结果,根据数量进行json生成并返回
		List<String> legendList = new ArrayList<String>(topicNum);
		List<NameValuePojo> seriesList = new ArrayList<NameValuePojo>(topicNum);
		for (int i = 0; i < topicNum && i < res.size(); i++) {
			legendList.add(res.get(i).getKey());
			seriesList.add(new NameValuePojo(res.get(i).getValue(), res.get(i).getKey()));
		}
		if (legendList.isEmpty()) {
			legendList.add("无");
			seriesList.add(new NameValuePojo(0, "无"));
		}
		JSONArray legendArr = JSONArray.parseArray(JSON.toJSONString(legendList));
		JSONArray seriesArr = JSONArray.parseArray(JSON.toJSONString(seriesList));
		JSONObject jo = new JSONObject();
		jo.put("legend", legendArr);
		jo.put("series", seriesArr);
		return jo;
	}

	@Override
	public JSONObject getYearBlogNum() {
		// 逐年获取数量进行封装
		List<String> yearStrList = new ArrayList<String>();
		List<Integer> blogPublishNumList = new ArrayList<Integer>();
		List<Integer> blogReadNumList = new ArrayList<Integer>();
		for (int i = MIN_YEAR; i < MAX_YEAR; i++) {
			// 年份添加
			String now = String.valueOf(i), next = String.valueOf(i + 1);
			yearStrList.add(now);
			// 博客年发表数量添加
			int num = csdnMapper.listYearBlogPublishNum(now, next);
			blogPublishNumList.add(num);
			// 博客年阅读量添加
			num = csdnMapper.listYearBlogReadNum(now, next);
			blogReadNumList.add(num);
		}
		// 封装成json返回
		JSONArray yearArr = JSONArray.parseArray(JSON.toJSONString(yearStrList));
		JSONArray blogPublishNumArr = JSONArray.parseArray(JSON.toJSONString(blogPublishNumList));
		JSONArray blogReadNumArr = JSONArray.parseArray(JSON.toJSONString(blogReadNumList));
		JSONObject jo = new JSONObject();
		jo.put("year", yearArr);
		jo.put("blogPublishNum", blogPublishNumArr);
		jo.put("blogReadNum", blogReadNumArr);
		return jo;
	}

	@Override
	public JSONObject getTopRankAuthor(int limit) {
		// mapper查询数据
		List<CSDNAuthor> authorList = csdnMapper.listTopRank(limit);
		return getByCSDNAuthorList(authorList);
	}

	@Override
	public JSONObject getAuthorPublishBlogWordCloud(int id) {
		// 根据Id获取博文列表
		List<CSDNBlog> blogList = csdnMapper.listAuthorBlogById(id);
		// 生成词频Map
		Map<String, Integer> wordCountMap = getWordCountMapByBlogList(blogList);
		return commonService.getKeywordByWordCountMap(wordCountMap);
	}
}
