package com.demo.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.project.model.UserRole;

public interface UserRoleRepo extends JpaRepository<UserRole, Long> {

}
