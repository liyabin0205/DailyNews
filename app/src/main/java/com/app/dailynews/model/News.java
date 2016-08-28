package com.app.dailynews.model;

public class News {
	
	private String title;
	private String detail;
	private String comment;
	private String discuss;
	private String imageUrl;
	
	@Override
	public String toString(){
		return "News [title=" + title + ", detail=" + detail + ", comment="
				+ comment + ", discuss="+ discuss +", imageUrl=" + imageUrl + "]";
	}
	
	public String getTitle(){
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDetail(){
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getDiscuss() {
		return discuss;
	}

	public void setDiscuss(String discuss) {
		this.discuss = discuss;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
