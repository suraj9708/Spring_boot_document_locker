package com.demo.project.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.project.dto.UserDtoIn;
import com.demo.project.dto.UserDtoOut;
import com.demo.project.dto.UserDtoSec;
import com.demo.project.dto.UserDtoThird;
import com.demo.project.dto.UserStatusDto;
import com.demo.project.dto.loginPayload;
import com.demo.project.model.Role;
import com.demo.project.model.User;
import com.demo.project.model.UserRole;
import com.demo.project.repository.RoleRepository;
import com.demo.project.repository.UserRepository;
import com.demo.project.repository.UserRoleRepo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired(required = true)
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRoleRepo userRoleRepo;

	// Service method for ragister the user
	@Override
	public UserDtoOut saveUser(UserDtoIn userDto) {
		User user = this.dtoToUser(userDto);

		Role role1 = roleRepository.findByName("ROLE_ADMIN");
		Role role2 = roleRepository.findByName("ROLE_USER");
		if (role1 == null) {

			role1 = getRole1();
		}

		if (role2 == null) {
			role2 = getRole2();
		}

		UserRole userRole = new UserRole();
		userRole.setRole(role2);
		userRole.setUser(user);
		this.userRoleRepo.save(userRole);

		User savedUser = this.userRepository.save(user);

		return this.userToDtoOut(savedUser);
	}

	// changing user to userdto and vice versa

	public User dtoToUser(UserDtoIn userDto) {
		User user = new User();
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setCred(userDto.getCred());

		return user;

	}

	private Role getRole1() {
		Role role1 = new Role();
		role1.setName("ROLE_ADMIN");

		return roleRepository.save(role1);
	}

	private Role getRole2() {
		Role role1 = new Role();
		role1.setName("ROLE_USER");

		return roleRepository.save(role1);
	}

	public UserDtoIn userToDto(User user) {
		UserDtoIn userDto = new UserDtoIn();
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setEmail(user.getEmail());
		userDto.setCred((user.getCred()));
		return userDto;
	}

	public UserDtoOut userToDtoOut(User user) {
		UserDtoOut userDtoOut = new UserDtoOut();
		userDtoOut.setFirstName(user.getFirstName());
		userDtoOut.setLastName(user.getLastName());
		userDtoOut.setEmail(user.getEmail());

		return userDtoOut;
	}

	// Service method for getting details of one user

	@Override
	public User getUser(long user_id) throws Exception {

		User us = this.userRepository.findById(user_id);
		return us;
	}

	// Service method for updating users details

	@Override
	public User updateUser(UserDtoSec userDto) throws Exception {
		User foundUser = this.userRepository.findById(userDto.getId());
		if (Objects.isNull(foundUser)) {
			return null;
		}
		foundUser.setId(userDto.getId());
		foundUser.setFirstName(userDto.getFirstName());
		foundUser.setLastName(userDto.getLastName());
		foundUser.setEmail(userDto.getEmail());
		foundUser.setAddress(userDto.getAddress());
		foundUser.setProfilePicS3Url(userDto.getProfile_pic_s3_url());

		return this.userRepository.save(foundUser);

	}

	// Service method for getting all users

	@Override
	public List<UserDtoSec> getAllUsers() throws Exception {
		List<User> listOfUsers = this.userRepository.findAll();
		List<UserDtoSec> listofusers = listOfUsers.stream().map((user) -> this.userToDtoSec(user))
				.collect(Collectors.toList());

		return listofusers;

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

	// Service method for updating Status of user

	@Override
	public UserDtoThird updateStatus(UserStatusDto userStatusDto, Integer userId) throws Exception {
		User user = this.userRepository.findById(userId);
		if (Objects.isNull(user)) {
			return null;

		}
		user.setActive(userStatusDto.isActive());
		User upateduser = this.userRepository.save(user);
		UserDtoThird UserDtoThird = new UserDtoThird();
		UserDtoThird.setId(upateduser.getId());
		UserDtoThird.setFirstName(upateduser.getFirstName());
		UserDtoThird.setLastName(upateduser.getLastName());
		UserDtoThird.setEmail(upateduser.getEmail());
		UserDtoThird.setAddress(upateduser.getAddress());
		UserDtoThird.setProfile_pic_s3_url(upateduser.getProfilePicS3Url());
		UserDtoThird.setActive(upateduser.isActive());

		return UserDtoThird;

	}

	// Service method for login user

	@Override
	public User loginUser(loginPayload user) {
		User us = this.userRepository.findBYUsernameAndcred(user.getUserName(), user.getCred());
		return us;
	}
}
