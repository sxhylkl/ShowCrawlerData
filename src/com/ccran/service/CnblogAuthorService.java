package com.ccran.service;

import java.util.List;

import com.ccran.pojo.CnblogAuthor;

public interface CnblogAuthorService {
	public List<CnblogAuthor> listTopFans(int limit);
}
