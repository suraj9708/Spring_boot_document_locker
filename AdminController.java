package com.demo.project.controller;

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
import com.demo.project.dto.UserDtoThird;
import com.demo.project.dto.UserStatusDto;
import com.demo.project.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private UserService userService;

	// getting list of users by admin
	@GetMapping("/users")
	public ResponseEntityDto getAllUsers() throws Exception {
		List<UserDtoSec> listOfUsers = this.userService.getAllUsers();

		if (listOfUsers.isEmpty()) {
			return ResponseEntityDto.create400Response("List of users  not found");
		}
		try {
			return ResponseEntityDto.create200Response(listOfUsers);
		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}

	}

	// Updating Status of users by admin
	@PutMapping("/users/{userId}")
	public ResponseEntityDto updateStatus(@RequestBody UserStatusDto userStatusDto,
			@PathVariable("userId") Integer userId) throws Exception {
		UserDtoThird user = this.userService.updateStatus(userStatusDto, userId);

		if (Objects.isNull(user)) {
			return ResponseEntityDto.create404Response("User does not exist");
		}

		try {
			return ResponseEntityDto.create200Response(user);

		} catch (Exception e) {
			return ResponseEntityDto.create500Response("Internal Error ");

		}

	}
}
