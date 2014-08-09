package com.estedge.base.request;

public class RecommendQuestionListRequest extends Request {
	

	public int getConNumberPage() {
		return conNumberPage;
	}

	public void setConNumberPage(int conNumberPage) {
		this.conNumberPage = conNumberPage;
	}

	public int getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(int indexPage) {
		this.indexPage = indexPage;
	}

	private int conNumberPage;
	private int indexPage;
}
