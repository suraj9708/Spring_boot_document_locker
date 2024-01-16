package com.demo.project.dto;

public class ResponseFile {
	private String docId;
	private String fileName;
	private String docS3Url;
	private String comments;

	public ResponseFile() {
	}

	public ResponseFile(String fileName, String s3Url, String comments) {
		super();
		this.fileName = fileName;
		docS3Url = s3Url;
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "ResponseFile [docId=" + docId + ", fileName=" + fileName + ", docS3Url=" + docS3Url + ", comments="
				+ comments + "]";
	}

	public ResponseFile(String id, String fileName, String s3Url, String comments) {
		super();
		this.docId = id;
		this.fileName = fileName;
		docS3Url = s3Url;
		this.comments = comments;
	}

	public ResponseFile(String fileName, String s3Url) {
		super();
		this.fileName = fileName;
		docS3Url = s3Url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getS3Url() {
		return docS3Url;
	}

	public void setS3Url(String s3Url) {
		docS3Url = s3Url;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
