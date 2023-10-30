package com.blogapp.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.UserDto;
import com.blogapp.services.UserService;
import com.blogapp.utils.ApiResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	//POST - CREATE USER
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto createUserDto=this.userService.createUser(userDto);
		
		return new ResponseEntity<>(createUserDto , HttpStatus.CREATED);
	}
	
	//PUT - UPDATE USER
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto , @PathVariable("userId") Integer uid)
	{
		UserDto updatedUser= this.userService.updateUser(userDto, uid);
		
		return ResponseEntity.ok(updatedUser);
	}
	
	//DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId)
	{
		this.userService.deleteUser(userId);
//		return new ResponseEntity(Map.of("messages" , "User Deleted Succesfully") , HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully" , true) , HttpStatus.OK);
	}
	
	//GET - get user
//	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer uid)
	{
		UserDto userById= this.userService.getUserById(uid);
		return ResponseEntity.ok(userById);
	}
	
	//GET - get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers()
	{
		List<UserDto> allUsers= this.userService.getAllUsers();
		return ResponseEntity.ok(allUsers);
	}
	
}
