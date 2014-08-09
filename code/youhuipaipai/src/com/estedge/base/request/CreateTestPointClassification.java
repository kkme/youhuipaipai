package com.estedge.base.request;

public class CreateTestPointClassification extends Request {
	/***
     * ��������
     */
private String exaTypeNm;
public String getExaTypeNm() {
		return exaTypeNm;
	}
	public void setExaTypeNm(String exaTypeNm) {
		this.exaTypeNm = exaTypeNm;
	}
	public String getCreateDateStr() {
		return createDateStr;
	}
	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	private String createDateStr;
}
