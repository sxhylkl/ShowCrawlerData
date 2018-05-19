package com.ccran.pojo;

/**
 * 
 * @author chenran
 * cnblog博主
 */
public class CnblogAuthor {
	private int id;
	private String authorNickName;
	private int fans;
	private int attention;
	private int readSum;
	
	public String getName() {
		return authorNickName;
	}
	public void setName(String name) {
		this.authorNickName = name;
	}
	public int getFans() {
		return fans;
	}
	public void setFans(int fans) {
		this.fans = fans;
	}
	public int getAttention() {
		return attention;
	}
	public void setAttention(int attetion) {
		this.attention = attetion;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReadSum() {
		return readSum;
	}
	public void setReadSum(int readSum) {
		this.readSum = readSum;
	}
}
