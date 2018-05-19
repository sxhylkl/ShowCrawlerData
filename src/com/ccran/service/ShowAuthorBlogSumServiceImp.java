package com.ccran.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccran.mapper.AuthorBlogMapper;

@Service
public class ShowAuthorBlogSumServiceImp implements ShowAuthroBlogSumService{
	@Autowired
	AuthorBlogMapper authorBlogMapper;
	
	@Override
	public int getAuthorSum() {
		return authorBlogMapper.getAuthorSum();
	}

	@Override
	public int getBlogSum() {
		return authorBlogMapper.getBlogSum();
	}

}
