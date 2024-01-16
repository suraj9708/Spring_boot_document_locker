package com.demo.project.dto;

public class UserStatusDto {
	private boolean isActive;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserStatusDto(boolean isActive) {
		super();
		this.isActive = isActive;
	}

	public UserStatusDto() {
		super();

	}

	@Override
	public String toString() {
		return "UserStatusDto [isActive=" + isActive + "]";
	}

}
