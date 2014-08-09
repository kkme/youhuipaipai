package com.example.youhuipaipai.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author lvning
 * @version create time:2014-5-18_下午5:05:31
 * @Description TODO
 */
public class BaseComments implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4601673428259161519L;

	private int result;
	private String all, good, bad;
	private List<Comment> data;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getGood() {
		return good;
	}
	public void setGood(String good) {
		this.good = good;
	}
	public String getBad() {
		return bad;
	}
	public void setBad(String bad) {
		this.bad = bad;
	}
	public List<Comment> getData() {
		return data;
	}
	public void setData(List<Comment> data) {
		this.data = data;
	}
}
