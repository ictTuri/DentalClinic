package com.clinic.dental.controllers;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.clinic.dental.model.user.dto.CustomResponseDto;
import com.clinic.dental.model.user.dto.UserRegisterDto;
import com.clinic.dental.model.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

	@InjectMocks
	RegisterController registerController;
	
	@Mock
	UserService userService;
	
	@BeforeEach
	void setup() {
		registerController = new RegisterController(userService);
	}
	
	@Test
	void whenSaveUser_ThenGetMessage() {
		UserRegisterDto dtoToRegister = new UserRegisterDto();
		dtoToRegister.setFirstName("test");
		dtoToRegister.setLastName("test");
		dtoToRegister.setAge(21);
		dtoToRegister.setEmail("test@gmail.com");
		dtoToRegister.setPassword("test");
		dtoToRegister.setPhone("0693232322");
		dtoToRegister.setUsername("testtest");
		dtoToRegister.setNID("K21212121L");
		
		when(userService.addClient(dtoToRegister)).thenReturn(new CustomResponseDto("success", LocalDateTime.now()));
		
		ResponseEntity<CustomResponseDto> response = registerController.registerClient(dtoToRegister);
		
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals("success", response.getBody().getMessage());
	}

}
