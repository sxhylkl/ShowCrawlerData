package com.ccran.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccran.mapper.CnblogAuthorMapper;
import com.ccran.pojo.CnblogAuthor;

@Service
public class CnblogAuthorServiceImp implements CnblogAuthorService {
	@Autowired
	CnblogAuthorMapper cnblogAuthorMapper;
	
	@Override
	public List<CnblogAuthor> listTopFans(int limit) {
		return cnblogAuthorMapper.listTopFans(limit);
	}

}
