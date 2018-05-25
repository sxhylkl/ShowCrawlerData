package com.ccran.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public interface CommonService {
	
	public List<Map.Entry<String, Integer>> getListOrderByMapValue(Map<String, Integer> map);
	
	public JSONObject getKeywordByWordCountMap(Map<String, Integer> wordCountMap);
}
