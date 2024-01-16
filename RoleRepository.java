package com.demo.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.project.model.Role;

 
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);

}
