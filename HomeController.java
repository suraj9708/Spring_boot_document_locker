package com.demo.project.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.demo.project.dto.loginPayload;
import com.demo.project.config.storage.s3Service;

import com.demo.project.dto.UserDtoIn;
import com.demo.project.dto.UserDtoOut;
import com.demo.project.dto.ResponseEntityDto;
import com.demo.project.model.User;
import com.demo.project.repository.UserRepository;
import com.demo.project.service.UserService;
import com.demo.project.validation.ValidationUtils;

@RestController
public class HomeController {
	@Autowired
	private UserService userService;

	@Autowired(required = true)
	private UserRepository userRepository;
	@Autowired
	private s3Service awsService;

	// for registration
	@PostMapping("/register")
	public ResponseEntityDto saveUser(@RequestBody UserDtoIn user) {
		List<String> messages = new ArrayList<>(4);

		if (ValidationUtils.isEmpty(user.getFirstName()) || (ValidationUtils.isNumeric(user.getFirstName()))
				|| (ValidationUtils.isNameValid(user.getFirstName()))) {
			messages.add("First name is invalid");
		}
		if (ValidationUtils.isEmpty(user.getLastName()) || ValidationUtils.isNumeric(user.getLastName())
				|| (ValidationUtils.isNameValid(user.getLastName()))) {
			messages.add("Last name is invalid");
		}
		if (ValidationUtils.isEmpty(user.getEmail()) || !(ValidationUtils.isEmailValid(user.getEmail()))) {

			messages.add("invalid Email Id");
		}
		if (ValidationUtils.isEmpty(user.getCred()) || !(ValidationUtils.isValidPassword(user.getCred()))) {
			messages.add("invalid password");
		}
		if (!messages.isEmpty()) {
			return ResponseEntityDto.create400Response(messages);
		}
        
		User us=this.userRepository.findByEmail(user.getEmail());
		
		if(user.equals(us)) {
			return ResponseEntityDto.create400Response("Email Id already exist");
		}
		
		
		try {
			UserDtoOut userDtoOut = this.userService.saveUser(user);
			return ResponseEntityDto.create200Response(userDtoOut);

		} catch (Exception e) {

			return ResponseEntityDto.create500Response("Internal Error ");

		}

	}

	// for login
	@PostMapping("/login")
	public ResponseEntityDto loginUser(@RequestBody loginPayload user) {
		List<String> messages = new ArrayList<>(2);

		if (ValidationUtils.isEmpty(user.getUserName()) || !(ValidationUtils.isEmailValid(user.getUserName()))) {

			messages.add("invalid user name!");
		}
		if (ValidationUtils.isEmpty(user.getCred()) || !(ValidationUtils.isValidPassword(user.getCred()))) {
			messages.add("invalid password !");
		}
		if (!messages.isEmpty()) {
			return ResponseEntityDto.create400Response(messages);
		}

		User us = this.userService.loginUser(user);
		if (Objects.isNull(us)) {
			return ResponseEntityDto.create400Response("Either user name or password is invalid");
		}

		try {

			UserDtoOut userDtoOut = this.userToDtoOut(us);
			return ResponseEntityDto.create200Response(userDtoOut);

		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}

	}

	public UserDtoOut userToDtoOut(User user) {
		UserDtoOut userDtoOut = new UserDtoOut();
		userDtoOut.setFirstName(user.getFirstName());
		userDtoOut.setLastName(user.getLastName());
		userDtoOut.setEmail(user.getEmail());

		return userDtoOut;
	}

	
	
	
	@PostMapping("/{userId}/uploadPhoto")
	public ResponseEntityDto uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("userId") long userId) {
		User user = this.userRepository.findById(userId);

		if (file.isEmpty()) {
			return ResponseEntityDto.create400Response("file not choosen");
		}
		try {
			String url = awsService.uploadFiles(file,user);
			user.setProfilePicS3Url(url);
			this.userRepository.save(user);
			System.out.println("uploaded");
			return ResponseEntityDto.create200Response(url);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
