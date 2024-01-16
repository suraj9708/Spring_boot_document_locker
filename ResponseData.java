package com.demo.project.dto;

public class ResponseData {
	private String docId;
	private String fileName;
	private String docS3Url;
	private String comments;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDocS3Url() {
		return docS3Url;
	}

	public void setDocS3Url(String docS3Url) {
		this.docS3Url = docS3Url;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public ResponseData() {
		super();
		// TODO Auto-generated constructor stub
	}
}
