package com.estedge.base.request;

public class QuestionListRequest extends Request {
	private int subjectId;
	private int knowlagePointId;
	private int testPointId;
	private int questionTypeId;
	private int questionDategoryId;
	private int errorTypeId;

	private int subjectName;

	private int knowlagePointName;

	private int testPointName;

	private int questionTypeName;

	private int questionDategoryName;

	private int errorTypeName;

	private boolean isCollection;

	private boolean isImportent;

	private int understandCount;

	public int getErrorType() {
		return errorTypeId;
	}

	public int getErrorTypeId() {
		return errorTypeId;
	}

	public int getErrorTypeName() {
		return errorTypeName;
	}

	public int getKnowlagePointId() {
		return knowlagePointId;
	}

	public int getKnowlagePointName() {
		return knowlagePointName;
	}

	public int getQuestionDategory() {
		return questionDategoryId;
	}

	public int getQuestionDategoryId() {
		return questionDategoryId;
	}

	public int getQuestionDategoryName() {
		return questionDategoryName;
	}

	public int getQuestionType() {
		return questionTypeId;
	}

	public int getQuestionTypeId() {
		return questionTypeId;
	}

	public int getQuestionTypeName() {
		return questionTypeName;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public int getSubjectName() {
		return subjectName;
	}

	public int getTestPoint() {
		return testPointId;
	}

	public int getTestPointId() {
		return testPointId;
	}

	public int getTestPointName() {
		return testPointName;
	}

	public int getUnderstandCount() {
		return understandCount;
	}

	public boolean isCollection() {
		return isCollection;
	}

	public boolean isImportent() {
		return isImportent;
	}

	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}

	public void setErrorType(int errorType) {
		this.errorTypeId = errorType;
	}

	public void setErrorTypeId(int errorTypeId) {
		this.errorTypeId = errorTypeId;
	}

	public void setErrorTypeName(int errorTypeName) {
		this.errorTypeName = errorTypeName;
	}

	public void setImportent(boolean isImportent) {
		this.isImportent = isImportent;
	}

	public void setKnowlagePointId(int knowlagePointId) {
		this.knowlagePointId = knowlagePointId;
	}

	public void setKnowlagePointName(int knowlagePointName) {
		this.knowlagePointName = knowlagePointName;
	}

	public void setQuestionDategory(int questionDategory) {
		this.questionDategoryId = questionDategory;
	}

	public void setQuestionDategoryId(int questionDategoryId) {
		this.questionDategoryId = questionDategoryId;
	}

	public void setQuestionDategoryName(int questionDategoryName) {
		this.questionDategoryName = questionDategoryName;
	}

	public void setQuestionType(int questionType) {
		this.questionTypeId = questionType;
	}

	public void setQuestionTypeId(int questionTypeId) {
		this.questionTypeId = questionTypeId;
	}

	public void setQuestionTypeName(int questionTypeName) {
		this.questionTypeName = questionTypeName;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public void setSubjectName(int subjectName) {
		this.subjectName = subjectName;
	}

	public void setTestPoint(int testPoint) {
		this.testPointId = testPoint;
	}

	public void setTestPointId(int testPointId) {
		this.testPointId = testPointId;
	}

	public void setTestPointName(int testPointName) {
		this.testPointName = testPointName;
	}

	public void setUnderstandCount(int understandCount) {
		this.understandCount = understandCount;
	}

}
