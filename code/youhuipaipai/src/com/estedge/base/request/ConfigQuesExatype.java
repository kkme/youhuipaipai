package com.estedge.base.request;

public class ConfigQuesExatype extends Request {
	 
	private String exaTypeId;

	public String getExaTypeId() {
		return exaTypeId;
	}

	public void setExaTypeId(String exaTypeId) {
		this.exaTypeId = exaTypeId;
	}

	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}

	private String createDateStr;
}
