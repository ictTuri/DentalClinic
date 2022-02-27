package com.clinic.dental.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.model.user.dto.CustomResponseDto;
import com.clinic.dental.model.user.dto.UserRegisterDto;
import com.clinic.dental.model.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "https://dental-clinic7.web.app/",allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("api/register")
public class RegisterController {
	
	private final UserService userService;
	
	@PostMapping
	public ResponseEntity<CustomResponseDto> registerClient(@Valid @RequestBody UserRegisterDto dto){
		return new ResponseEntity<>(userService.addClient(dto),HttpStatus.CREATED);
	}

}
