package com.estedge.base.request;

public class SaveTestPointThink extends Request {
	/***
	 * ���濼����෴˼�ӿ�
	 */
	private String testPointCateId;

	public String getTestPointCateId() {
		return testPointCateId;
	}

	public void setTestPointCateId(String testPointCateId) {
		this.testPointCateId = testPointCateId;
	}

	public String getTextViewContent() {
		return textViewContent;
	}

	public void setTextViewContent(String textViewContent) {
		this.textViewContent = textViewContent;
	}

	public String getVoiceViewUrls() {
		return voiceViewUrls;
	}

	public void setVoiceViewUrls(String voiceViewUrls) {
		this.voiceViewUrls = voiceViewUrls;
	}

	private String textViewContent;
	private String voiceViewUrls;
}
