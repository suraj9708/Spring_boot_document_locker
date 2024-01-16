package com.demo.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.project.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findById(long userId);

	public User findByEmail(String email);

	@Query("select u From User u WHERE u.email= :a AND u.cred=:b ")
	public User findBYUsernameAndcred(@Param("a") String userName, @Param("b") String cred);

}
