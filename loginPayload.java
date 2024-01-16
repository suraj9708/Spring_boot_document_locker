package com.demo.project.dto;

public class loginPayload {

	private String userName;
	private String cred;

	public loginPayload() {
		super();

	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCred() {
		return cred;
	}

	public void setCred(String cred) {
		this.cred = cred;
	}

}
