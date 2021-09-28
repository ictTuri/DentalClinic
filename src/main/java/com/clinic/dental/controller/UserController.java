package com.clinic.dental.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.dto.UserDto;
import com.clinic.dental.model.UserEntity;

@RestController
@RequestMapping("api/users")
public class UserController {

	@GetMapping
	public ResponseEntity<List<UserEntity>> getAllUsers(){
		return null;
	}
	
	@GetMapping("{id}")
	public ResponseEntity<UserEntity> getUserById(@RequestParam("id") Long id){
		return null;
	}
	
	@PostMapping
	public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserDto userDto){
		
	}
}
