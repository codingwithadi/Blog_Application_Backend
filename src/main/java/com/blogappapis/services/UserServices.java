package com.blogappapis.services;

import java.util.List;

import com.blogappapis.payloads.UserDTO;

public interface UserServices {
	
	//creating all methods for user services 
	
	UserDTO createUser(UserDTO user);
	
	UserDTO updateUser(UserDTO user, Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUserById(Integer userId);

}
