package com.ccran.pojo;

/**
 * 
 * @author chenran
 * 对博客数据项的简单封装
 */
public class CnblogBlog {
	private String title;
	private String tag;
	private String type;
	private int readNum;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getReadNum() {
		return readNum;
	}
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
}
