package com.ccran.pojo;

/**
 * 对Name、Value的简单封装
 * @author chenran
 *
 */
public class NameValuePojo {
	private int value;
	private String name;
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public NameValuePojo(int value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
	public NameValuePojo() {
		super();
	}
}
