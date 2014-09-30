package com.zd.lbsx.bean;

public class Info {
	int news_id;
	String news_name;
	String news_content;
	
	
	public Info(int news_id, String news_name, String news_content) {
		super();
		this.news_id = news_id;
		this.news_name = news_name;
		this.news_content = news_content;
	}
	
	public int getNews_id() {
		return news_id;
	}
	public void setNews_id(int news_id) {
		this.news_id = news_id;
	}
	public String getNews_name() {
		return news_name;
	}
	public void setNews_name(String news_name) {
		this.news_name = news_name;
	}
	public String getNews_content() {
		return news_content;
	}
	public void setNews_content(String news_content) {
		this.news_content = news_content;
	}
	
}
