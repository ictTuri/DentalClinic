package com.clinic.dental.controllers;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.model.user.dto.UserClinicDataDto;
import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("api/users")
public class UserController {
	
	private final UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return new ResponseEntity<List<UserDto>>(userService.getAllUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/role")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY','ROLE_DOCTOR','ROLE_PUBLIC')")
	public ResponseEntity<String> getRole(){
		return new ResponseEntity<String>(userService.getRole(),HttpStatus.OK);
	}
	
	@GetMapping("/profile")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY','ROLE_DOCTOR','ROLE_PUBLIC')")
	public ResponseEntity<UserDto> userProfile(){
		return new ResponseEntity<UserDto>(userService.userProfile(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id){
		return new ResponseEntity<UserDto>(userService.getUserById(id),HttpStatus.OK);
	}
	
	@GetMapping("client-data/{credentials}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DOCTOR')")
	public ResponseEntity<UserClinicDataDto> getUserDataByCredentials(@PathVariable("credentials") @NotBlank String credentials){
		return new ResponseEntity<UserClinicDataDto>(userService.getUserDataByCredentials(credentials),HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userService.createUser(userDto),HttpStatus.CREATED);
	}
	
	@PutMapping("/profile")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY','ROLE_DOCTOR','ROLE_PUBLIC')")
	public ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UserDto userDto){
		return new ResponseEntity<UserDto>(userService.updateProfile(userDto),HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<UserDto> updateUserById(@Valid @RequestBody UserDto userDto, @PathVariable("id") Long id){
		return new ResponseEntity<UserDto>(userService.updateUserById(userDto,id),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteUseryId(@PathVariable("id") Long id){
		return new ResponseEntity<Void>(userService.deleteUserById(id),HttpStatus.NO_CONTENT);
	}
	
}
