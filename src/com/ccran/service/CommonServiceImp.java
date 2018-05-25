package com.ccran.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ccran.pojo.NameValuePojo;

@Service
public class CommonServiceImp implements CommonService {
	/**
	 * map根据value排序
	 * @return
	 */
	@Override
	public List<Map.Entry<String, Integer>> getListOrderByMapValue(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> res = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(res, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});
		return res;
	}

	/**
	 * 通过词频Map结构（词-频率）封装成name-value数组，同时作为series的key值JSONObject返回
	 * @return
	 */
	@Override
	public JSONObject getKeywordByWordCountMap(Map<String, Integer> wordCountMap) {
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

}
