package com.clinic.dental.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.clinic.dental.model.user.converter.UserConverter;
import com.clinic.dental.model.user.dto.UserClinicDataDto;
import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.service.UserService;
import com.clinic.dental.util.user.UserClinicDataUtilTest;
import com.clinic.dental.util.user.UserUtilTest;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	@BeforeEach
	void setup() {
		userController = new UserController(userService);
	}
	
	@Test
	void givenUserList_WhenGetThemAll_ValidateSize() {
		List<UserDto> users = new ArrayList<>();
		users.add(UserConverter.toDto(UserUtilTest.doctorOne()));
		users.add(UserConverter.toDto(UserUtilTest.publicOne()));
		users.add(UserConverter.toDto(UserUtilTest.secretaryOne()));
		
		when(userService.getAllUsers()).thenReturn(users);
		
		ResponseEntity<List<UserDto>> usersDto = userController.getAllUsers();
		
		assertEquals(3, usersDto.getBody().size());
		assertEquals(HttpStatus.OK, usersDto.getStatusCode());
	}

	@Test
	void givenUser_WhenGetById_ValidateUser() {
		Long id = 1L;
		UserDto user = UserConverter.toDto(UserUtilTest.doctorOne());
		
		when(userService.getUserById(id)).thenReturn(user);
		
		ResponseEntity<UserDto> usersDto = userController.getUserById(id);
		
		assertNotNull(usersDto);
		assertEquals("DoctorOne", usersDto.getBody().getFirstName());
		assertEquals(HttpStatus.OK, usersDto.getStatusCode());
	}
	
	@Test
	void givenUser_WhenCreate_ValidateStatus() {
		UserDto user = UserConverter.toDto(UserUtilTest.doctorOne());
		
		when(userService.createUser(user)).thenReturn(user);
		
		ResponseEntity<UserDto> usersDto = userController.createUser(user);
		
		assertNotNull(usersDto);
		assertEquals("DoctorOne", usersDto.getBody().getFirstName());
		assertEquals(HttpStatus.CREATED, usersDto.getStatusCode());
	}
	
	@Test
	void givenUser_WhenUpdateId_ValidateUserStatus() {
		Long id = 1L;
		UserDto user = UserConverter.toDto(UserUtilTest.doctorOne());
		
		when(userService.updateUserById(user, id)).thenReturn(user);
		
		ResponseEntity<UserDto> usersDto = userController.updateUserById(user, id);
		
		assertNotNull(usersDto);
		assertEquals("DoctorOne", usersDto.getBody().getFirstName());
		assertEquals(HttpStatus.CREATED, usersDto.getStatusCode());
	}
	
	@Test
	void givenUserId_WhenDelete_ValidateStatus() {
		Long id = 1L;
		
		doNothing().when(userService).deleteUserById(id);
		
		ResponseEntity<Void> voidResponse = userController.deleteUseryId(id);
		
		assertNotNull(voidResponse);
		assertEquals(HttpStatus.NO_CONTENT, voidResponse.getStatusCode());
	}
	
	@Test
	void givenCredentials_WhenGEtUserData_ValidateStatus() {
		String credentials = "G00000000D";
		UserClinicDataDto user = UserClinicDataUtilTest.dataUserOne();
		
		when(userService.getUserDataByCredentials(credentials)).thenReturn(user);
		
		ResponseEntity<UserClinicDataDto> userRetrieved = userController.getUserDataByCredentials(credentials);
		
		assertNotNull(userRetrieved);
		assertEquals(HttpStatus.OK, userRetrieved.getStatusCode());
	}
}
