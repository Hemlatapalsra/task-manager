package com.taskmanager.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskmanager.model.Users;
import com.taskmanager.repository.UserRepository;

@Service
public class UserService {
	@Autowired private UserRepository userRepository;
	@Autowired private PasswordEncoder passwordEncoder;
		
	public void save(Users user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
	

    public boolean userExists(String username, String email) {
        return userRepository.existsByUsername(username) || userRepository.existsByEmail(email);
    }
    
    public void register(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
    
    public Users findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

}
