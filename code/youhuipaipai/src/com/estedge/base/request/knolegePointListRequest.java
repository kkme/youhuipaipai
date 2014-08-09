package com.estedge.base.request;

public class knolegePointListRequest extends Request {
	private int subjectId;

	public knolegePointListRequest(int subjectId, String studyLevel) {
		super();
		this.subjectId = subjectId;
		this.studyLevel = studyLevel;
	}

	private String studyLevel;

	public String getStudyLevel() {
		return studyLevel;
	}

	public void setStudyLevel(String studyLevel) {
		this.studyLevel = studyLevel;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

}
