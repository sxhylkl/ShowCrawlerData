package com.ccran.mapper;

import java.util.List;

import com.ccran.pojo.CnblogAuthor;

/**
 * 
 * @author chenran
 * CnblogAuthor操作
 */
public interface CnblogAuthorMapper {
	//粉丝量最多的前num个
	public List<CnblogAuthor> listTopFans(int limit);
}
