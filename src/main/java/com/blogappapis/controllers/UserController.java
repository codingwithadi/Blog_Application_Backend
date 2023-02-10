package com.blogappapis.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blogappapis.payloads.UserDTO;
import com.blogappapis.services.UserServices;
import com.blogappapis.utilities.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user")
@EnableMethodSecurity
public class UserController {

	@Autowired(required = true)
	private UserServices userService;

	// api for create user using POST Mapping

	@PostMapping("/createUser")
	public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {

		UserDTO createUserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<>(createUserDTO, HttpStatus.CREATED);
	}

	// api for update existing user using PUT Mapping
	// {userId} is a path variable which is for particular user update. so we can
	// use @PathVariable

	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") Integer userId) {

		UserDTO updateUser = this.userService.updateUser(userDTO, userId);

		// here we use ResponseEntity.ok() directly

		return ResponseEntity.ok(updateUser);
	}

	// api for delete existing user using DELETE Mapping

	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {

		this.userService.deleteUserById(userId);

		// ApiResponse is a Utility class Which is provide responseMessages and
		// responseResults

		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully!!", true), HttpStatus.OK);
	}

	// api for get all users using GET Mapping
	// Only Admin Role User can view All User List
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers() {

		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	// api for get single user using GET Mapping
	
	@GetMapping("/getUser/{userId}")
	public ResponseEntity<UserDTO> getSingleUser(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

}
