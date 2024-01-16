package com.demo.project.dto;

public class UserDtoSec {
	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String profile_pic_s3_url;

	public long getId() {
		return id;
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

	public void setId(long id) {
		this.id = id;
	}

	public UserDtoSec() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDtoSec(long id, String firstName, String lastName, String email, String address,
			String profile_pic_s3_url) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.profile_pic_s3_url = profile_pic_s3_url;
	}

}
