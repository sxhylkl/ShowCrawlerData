package com.ccran.pojo;

public class CSDNBlog {
	private String title;
	private String tag;
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
	public int getReadNum() {
		return readNum;
	}
	public void setReadNum(int readNum) {
		this.readNum = readNum;
	}
	public CSDNBlog(String title, String tag, int readNum) {
		super();
		this.title = title;
		this.tag = tag;
		this.readNum = readNum;
	}
	public CSDNBlog() {
		super();
	}
}
