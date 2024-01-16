package com.demo.project.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

public class ResponseEntityDto {
	private int httpStatusCode;
	private boolean status;
	private List<String> messages;
	private Object data;

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public static ResponseEntityDto create200Response(Object data) {
		ResponseEntityDto dto = new ResponseEntityDto();
		dto.status = true;
		dto.httpStatusCode = HttpStatus.OK.value();
		dto.setMessage("Request processed successfully");
		dto.setData(data);
		return dto;
	}

	public void setMessage(String messages) {
		if (Objects.isNull(this.messages)) {
			this.messages = new ArrayList<>();
		}
		this.messages.add(messages);
	}

	public void setMessage(List<String> messages) {
		if (Objects.isNull(this.messages)) {
			this.messages = new ArrayList<>();
		}

		this.messages.addAll(messages);

	}

	public static ResponseEntityDto create500Response(String messages) {
		ResponseEntityDto dto = new ResponseEntityDto();
		dto.status = false;
		dto.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		dto.setMessage(messages);
		return dto;
	}

	public static ResponseEntityDto create500Response(List<String> messages) {
		ResponseEntityDto dto = new ResponseEntityDto();
		dto.status = false;
		dto.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
		dto.setMessage(messages);
		return dto;
	}

	public static ResponseEntityDto create400Response(List<String> messages) {
		ResponseEntityDto dto = new ResponseEntityDto();
		dto.status = false;
		dto.httpStatusCode = HttpStatus.BAD_REQUEST.value();
		dto.setMessage(messages);
		return dto;
	}

	public static ResponseEntityDto create400Response(String messages) {
		ResponseEntityDto dto = new ResponseEntityDto();
		dto.status = false;
		dto.httpStatusCode = HttpStatus.BAD_REQUEST.value();
		dto.setMessage(messages);
		return dto;
	}

	public static ResponseEntityDto create404Response(String messages) {
		ResponseEntityDto dto = new ResponseEntityDto();
		dto.status = false;
		dto.httpStatusCode = HttpStatus.NOT_FOUND.value();
		dto.setMessage(messages);
		return dto;
	}

}
