package com.example.youhuipaipai.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public  class LoadBean  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3177561533403525250L;
	private int result;
	private String info;
	private List<LoadingImage> data;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public List<LoadingImage> getData() {
		return data;
	}
	public void setData(List<LoadingImage> data) {
		this.data = data;
	}
	
}
