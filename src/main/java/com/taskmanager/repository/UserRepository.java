package com.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskmanager.model.Users;

@Repository
public interface UserRepository  extends JpaRepository<Users, Long> {
	
    Users findByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByEmail(String email);

}
