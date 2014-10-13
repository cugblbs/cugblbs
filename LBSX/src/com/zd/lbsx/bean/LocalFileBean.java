package com.zd.lbsx.bean;

public class LocalFileBean {
	String filename;
	long size;
	String urlPath;
	
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public LocalFileBean(String filename, long filesize,String url) {
		super();
		this.filename = filename;
		this.size = filesize;
		this.urlPath=url;
	}
	
}
