package com.demo.project.dto;

public class UserDtoThird {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String profile_pic_s3_url;
	private boolean isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfile_pic_s3_url() {
		return profile_pic_s3_url;
	}

	public void setProfile_pic_s3_url(String profile_pic_s3_url) {
		this.profile_pic_s3_url = profile_pic_s3_url;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public UserDtoThird() {
		super();

	}

}
