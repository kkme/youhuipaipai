package com.estedge.base.request;

public class StudentListRequest extends Request {
	private String province;

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public boolean isMyPayAttention() {
		return myPayAttention;
	}

	public void setMyPayAttention(boolean myPayAttention) {
		this.myPayAttention = myPayAttention;
	}

	public int getAttention() {
		return attention;
	}

	public void setAttention(int attention) {
		this.attention = attention;
	}

	public int getCollection() {
		return collection;
	}

	public void setCollection(int collection) {
		this.collection = collection;
	}

	public int getPraise() {
		return praise;
	}

	public void setPraise(int praise) {
		this.praise = praise;
	}

	public int getErrorQuestionNumber() {
		return errorQuestionNumber;
	}

	public void setErrorQuestionNumber(int errorQuestionNumber) {
		this.errorQuestionNumber = errorQuestionNumber;
	}

	private boolean myPayAttention;
	private int attention;
	private int collection;
	private int praise;
	private int errorQuestionNumber;
}
