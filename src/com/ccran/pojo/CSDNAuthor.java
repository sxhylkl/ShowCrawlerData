package com.ccran.pojo;

public class CSDNAuthor {
	private int authorId;
	private String authorName;
	private int blogNum;
	private int fansNum;
	private int likeNum;
	private int commentNum;
	private int levelNum;
	private int visitNum;
	private int integral;
	
	public CSDNAuthor(){}
	
	public CSDNAuthor(int authorId,String authorName, int blogNum, int fansNum, int likeNum, int commentNum, int level, int visitNum,
			int integral) {
		super();
		this.authorId=authorId;
		this.authorName = authorName;
		this.blogNum = blogNum;
		this.fansNum = fansNum;
		this.likeNum = likeNum;
		this.commentNum = commentNum;
		this.levelNum = level;
		this.visitNum = visitNum;
		this.integral = integral;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public int getBlogNum() {
		return blogNum;
	}
	public void setBlogNum(int blogNum) {
		this.blogNum = blogNum;
	}
	public int getFansNum() {
		return fansNum;
	}
	public void setFansNum(int fansNum) {
		this.fansNum = fansNum;
	}
	public int getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(int likeNum) {
		this.likeNum = likeNum;
	}
	public int getCommentNum() {
		return commentNum;
	}
	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}
	public int getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(int level) {
		this.levelNum = level;
	}
	public int getVisitNum() {
		return visitNum;
	}
	public void setVisitNum(int visitNum) {
		this.visitNum = visitNum;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}

	/**
	 * @return the authorId
	 */
	public int getAuthorId() {
		return authorId;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
}
