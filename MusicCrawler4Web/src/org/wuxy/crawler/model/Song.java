package org.wuxy.crawler.model;

public class Song {

	private Integer id;
	private String url;
	private String title;
	private Integer commentCount;
	
	
	public Song() {
		super();
	}
	public Song(String url, String title) {
		super();
		this.url = url;
		this.title = title;
	}
	public Song(String url, String title, Integer commentCount) {
		super();
		this.url = url;
		this.title = title;
		this.commentCount = commentCount;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int i) {
		this.commentCount = i;
	}
	@Override
	public String toString() {
		return "Song [url=" + url + ", title=" + title + ", commentCount=" + commentCount + "]";
	}
	
}
