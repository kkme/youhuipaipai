package com.estedge.base.request;

public class DelQueRel extends Request {
	/**
	 * ������Ŀ��ϵɾ��ӿ�
	 */
	private String testPointId;

	public String getTestPointId() {
		return testPointId;
	}

	public void setTestPointId(String testPointId) {
		this.testPointId = testPointId;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	private String questionId;
}
