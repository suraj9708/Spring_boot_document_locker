package com.demo.project.service;

import java.util.List;

import com.demo.project.dto.UserDtoIn;
import com.demo.project.dto.UserDtoOut;

import com.demo.project.dto.UserDtoSec;
import com.demo.project.dto.UserDtoThird;
import com.demo.project.dto.UserStatusDto;
import com.demo.project.dto.loginPayload;
import com.demo.project.model.User;

public interface UserService {
	public UserDtoOut saveUser(UserDtoIn userDto);

	public User getUser(long userId) throws Exception;

	public UserDtoThird updateStatus(UserStatusDto userStatusDto, Integer user_id) throws Exception;

	public User updateUser(UserDtoSec user) throws Exception;

	public List<UserDtoSec> getAllUsers() throws Exception;

	public User loginUser(loginPayload user);
}
