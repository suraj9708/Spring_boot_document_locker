package com.demo.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.demo.project.dto.ResponseEntityDto;
import com.demo.project.dto.UserDtoSec;
import com.demo.project.model.User;
import com.demo.project.service.UserService;
import com.demo.project.validation.ValidationUtils;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserService userService;

	// Get deatils of loged in user
	@GetMapping("/{userId}")
	public ResponseEntityDto getUser(@PathVariable("userId") long userId) throws Exception {
		User user = this.userService.getUser(userId);

		if (Objects.isNull(user)) {
			return ResponseEntityDto.create404Response("User does not exist");
		}
		try {
			UserDtoSec userDto = this.userToDto(user);
			return ResponseEntityDto.create200Response(userDto);
		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");
		}

	}

	public UserDtoSec userToDto(User user) {
		UserDtoSec userDto = new UserDtoSec();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setAddress(user.getAddress());
		userDto.setProfile_pic_s3_url(user.getProfilePicS3Url());
		return userDto;
	}

	// Updating user details of loged in user
	@PutMapping("/update")
	public ResponseEntityDto updateUser(@RequestBody UserDtoSec userDto) throws Exception {
		List<String> messages = new ArrayList<>(4);
 
		if (ValidationUtils.isEmpty(userDto.getFirstName()) ||(ValidationUtils.isNumeric(userDto.getFirstName()))
				|| (ValidationUtils.isNameValid(userDto.getFirstName()))) {
			messages.add("First name is invalid");
		}
		if (ValidationUtils.isEmpty(userDto.getLastName()) || ValidationUtils.isNumeric(userDto.getLastName())
				|| (ValidationUtils.isNameValid(userDto.getLastName()))) {
			messages.add("Last name is invalid");
		} 
		if (ValidationUtils.isEmpty(userDto.getEmail()) || !(ValidationUtils.isEmailValid(userDto.getEmail()))) {

			messages.add("invalid Email Id");
		}
		if (ValidationUtils.isEmpty(userDto.getAddress())) {
			messages.add("invalid address");
		}

		if (!messages.isEmpty()) {
			return ResponseEntityDto.create400Response(messages);
		}

		try {
			User updateuser = this.userService.updateUser(userDto);
			if (Objects.isNull(updateuser)) {
				return ResponseEntityDto.create404Response("User does not exist to update");
			}
			UserDtoSec userDtoSec = this.userToDtoSec(updateuser);
			return ResponseEntityDto.create200Response(userDtoSec);

		} catch (Exception e) {

			return ResponseEntityDto.create500Response("Internal Error ");

		}

	}

	private UserDtoSec userToDtoSec(User user) {
		UserDtoSec userDto = new UserDtoSec();
		userDto.setId(user.getId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setAddress(user.getAddress());
		userDto.setProfile_pic_s3_url(user.getProfilePicS3Url());
		return userDto;
	}
}
