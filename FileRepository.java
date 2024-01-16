package com.demo.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.project.model.User;
import com.demo.project.model.UserDocs;

public interface FileRepository extends JpaRepository<UserDocs, String> {

	UserDocs getById(String docId);

	@Query("select d from UserDocs d Where d.id=:a AND d.user=:b")
	UserDocs getByIdAndUser(@Param("a") String id, @Param("b") User user);

	@Query("select d from UserDocs d Where d.user=:b")
	List<UserDocs> findAll(@Param("b") User user);

	@Query("select d from UserDocs d Where d.fileName=:a AND d.user=:b")
	UserDocs getBydocAndUser(@Param("a") String docName, @Param("b") User user);
}
