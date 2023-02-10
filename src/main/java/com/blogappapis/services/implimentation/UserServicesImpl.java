package com.blogappapis.services.implimentation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappapis.entities.User;
import com.blogappapis.exceptions.ResourceNotFoundException;
import com.blogappapis.payloads.UserDTO;
import com.blogappapis.repos.UserRepo;
import com.blogappapis.services.UserServices;

@Service
public class UserServicesImpl implements UserServices {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	// Inserting user
	@Override
	public UserDTO createUser(UserDTO userDto) {

		// First userDto convert to user and then save user using jpa method.
		// again convert user to userDto and return userDto to method.
		User user = this.dtoTOuser(userDto);
		User savedUser = userRepo.save(user);
		return this.userTOdto(savedUser);
	}

	// Updating user
	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {

		// get user using userid then set updated values and save new value.
		// first find user and if user not found then throw custom exception
		// ResourceNotFoundException.

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		// updating data, brining from userDto and set to the user.

		user.setUserName(userDto.getUserName());
		user.setUserEmailId(userDto.getUserEmailId());
		user.setUserPassword(userDto.getUserPassword());
		user.setUserAbout(userDto.getUserPassword());

		// save updated data

		User updatedUser = this.userRepo.save(user);

		// converting user to userDto and return to userDto.
		return this.userTOdto(updatedUser);

	}

	// GetUserById
	@Override
	public UserDTO getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return this.userTOdto(user);
	}

	// GetAllUser
	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepo.findAll();
		// here we are extracting single user and convert into usertoDto because we
		// returns the UserDTO
		// here we are used Lambda function. stream(), map() etc
		List<UserDTO> userDtos = users.stream().map(user -> this.userTOdto(user)).collect((Collectors.toList()));
		return userDtos;
	}

	// Delete User By Using Id
	@Override
	public void deleteUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}

	// Model Mapping means to access one object in another. Entity field must be
	// same.
	// model mapper is automatically determine class and manage object in another
	// object.
	// here we are doing conversion between UserDTO to User and viceversa.
	// we are using UserDTO for transfer data object so thats why we are doing this.

	public User dtoTOuser(UserDTO userDto) {

//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		User user = this.modelMapper.map(userDto, User.class);

		// ModelMapping with manually
//			User user = new User();
//			user.setUser_Id(userDto.getUserId());
//			user.setUserName(userDto.getUserName());
//			user.setUserEmailId(userDto.getUserEmailId());
//			user.setUserPassword(userDto.getUserPassword());
//			user.setUserAbout(userDto.getUserAbout());

		return user;

	}

	public UserDTO userTOdto(User user) {

//		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO userDto = this.modelMapper.map(user, UserDTO.class);

//			UserDTO userDto = new UserDTO();
//			userDto.setUser_Id(user.getUser_Id());
//			userDto.setUserName(user.getUserName());
//			userDto.setUserEmailId(user.getUserEmailId());
//			userDto.setUserPassword(user.getUserPassword());
//			userDto.setUserAbout(user.getUserAbout());

		return userDto;
	}

}
