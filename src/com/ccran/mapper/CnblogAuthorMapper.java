package com.ccran.mapper;

import java.util.List;

import com.ccran.pojo.CnblogAuthor;

/**
 * 
 * @author chenran
 * CnblogAuthor����
 */
public interface CnblogAuthorMapper {
	//��˿������ǰnum��
	public List<CnblogAuthor> listTopFans(int limit);
}
