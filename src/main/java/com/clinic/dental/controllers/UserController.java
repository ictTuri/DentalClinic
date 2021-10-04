package com.clinic.dental.controllers;

import java.util.List;

import javax.validation.Valid;

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

import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
	
	private final UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<List<UserDto>>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
		return new ResponseEntity<UserDto>(userService.getUserById(id),HttpStatus.OK);
	}
	
	@PostMapping
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userService.createUser(userDto),HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> updateUserById(@Valid @RequestBody UserDto userDto, @PathVariable("id") Long id){
		return new ResponseEntity<UserDto>(userService.updateUserById(userDto,id),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteUseryId(@PathVariable("id") Long id){
		return new ResponseEntity<Void>(userService.deleteUserById(id),HttpStatus.NO_CONTENT);
	}
	
}
