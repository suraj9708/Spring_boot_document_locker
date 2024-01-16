package com.demo.project.dto;

public class DocStatusDto {
	private boolean isActive;

	public boolean isIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public DocStatusDto(boolean isActive) {
		super();
		this.isActive = isActive;
	}

	public DocStatusDto() {
		super();
	}

	@Override
	public String toString() {
		return "DocStatusDto [isActive=" + isActive + "]";
	}
}
