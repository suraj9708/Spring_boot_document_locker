package com.demo.project.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_docs")
public class UserDocs {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@Column(name = "doc_name")
	private String fileName;

	@Column(name = "doc_ext")
	private String fileType;

	private String s3Url;

	private boolean isActive = true;
	private String comments;
	@CreationTimestamp
	private LocalDateTime createdOn;
	@UpdateTimestamp
	private LocalDateTime updatedOn;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String gets3Url() {
		return s3Url;
	}

	public void sets3Url(String s3Url) {
		this.s3Url = s3Url;
	}

	public boolean isisActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public UserDocs() {

	}

	@Override
	public String toString() {
		return "UserDocs [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", s3Url=" + s3Url
				+ ", isActive=" + isActive + ", comments=" + comments + "]";
	}

	public UserDocs(String fileName, String fileType, String comments) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.comments = comments;
	}

}
