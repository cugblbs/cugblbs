package com.zd.lbsx.bean;

public class LocalFileBean {
	String filename;
	long size;
	
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
	public LocalFileBean(String filename, long filesize) {
		super();
		this.filename = filename;
		this.size = filesize;
	}
	
}
