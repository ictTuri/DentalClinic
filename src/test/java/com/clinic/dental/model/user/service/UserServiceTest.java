package com.clinic.dental.model.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.converter.UserConverter;
import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.repository.UserRepository;
import com.clinic.dental.model.user.service.impl.UserServiceImpl;
import com.clinic.dental.util.user.UserUtilTest;

@SpringBootTest
@Transactional
class UserServiceTest {
	@InjectMocks
	UserServiceImpl userService;
	@Mock
	UserRepository userRepo;
	@Mock
	PasswordEncoder passEncoder;
	@Mock
	AppointmentRepository appointmentRepo;
	
	Authentication authentication = mock(Authentication.class);
	
	private UserEntity user1, user2, user3;
	
	@BeforeEach
	public void setup() {
		user1 = UserUtilTest.publicTwo();
		user2 = UserUtilTest.doctorOne();
		user3 = UserUtilTest.secretaryTwo();
	}
	
	@Test
	void givenList_WhenGettAll_ValidateSize() {
		List<UserEntity> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		users.add(user3);
		
		when(userRepo.findAll()).thenReturn(users);
		
		List<UserDto> dtos = userService.getAllUsers();
		assertEquals(3, dtos.size());
		assertNotNull(dtos);
	}
	
	@Test
	void givenId_WhenGetUser_ValidateUserData() {
		long id = 1L;
		when(userRepo.locateById(id)).thenReturn(user1);
		
		UserDto dto = userService.getUserById(id);
		assertNotNull(dto);
		assertEquals("ClientTwo", dto.getFirstName());
	}

	@Test
	void givenEmail_WhenValidateExistence_TurnFalse() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = UserServiceImpl.class.getDeclaredMethod("existUserByEmail", String.class);
		method.setAccessible(true);
		String email = "doesnotexist@gmail.com";
		boolean existEmail = (boolean) method.invoke(userService, email);
		assertFalse(existEmail);
	}
	
	@Test
	void givenPhone_WhenValidateExistence_TurnFalse() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = UserServiceImpl.class.getDeclaredMethod("existUserByPhone", String.class);
		method.setAccessible(true);
		String phone = "0699999999";
		boolean existPhone = (boolean) method.invoke(userService, phone);
		assertFalse(existPhone);
	}
	
	@Test
	void givenNID_WhenValidateExistence_TurnFalse() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = UserServiceImpl.class.getDeclaredMethod("existUserByNID", String.class);
		method.setAccessible(true);
		String NID = "G00000001F";
		boolean existNID = (boolean) method.invoke(userService, NID);
		assertFalse(existNID);
	}
	
	@Test
	void givenUsername_WhenValidateExistence_TurnFalse()throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
		Method method = UserServiceImpl.class.getDeclaredMethod("existUserByUsername", String.class);
		method.setAccessible(true);
		String username = "doesnotexist";
		boolean existUsername = (boolean) method.invoke(userService, username);
		assertFalse(existUsername);
	}
	
	@Test
	void givenDtoAndId_WhenUpdateUser_Validate() {
		UserDto dto = UserConverter.toDto(user1);
		long id = 1L;
		when(userRepo.locateById(id)).thenReturn(user1);
		when(userRepo.save(user1)).thenReturn(null);
		
		UserDto dtoToGet = userService.updateUserById(dto, id);
		assertNotNull(dtoToGet);
	}
	
	@Test
	void givenId_WhenDelete_GetNothing() {
		long id = 1L;
		doNothing().when(userRepo).deleteById(id);
		assertDoesNotThrow(()-> userService.deleteUserById(id));
	}
	
	@Test
	void givenArray_whenGetDoctors_validateSize() {
		String role = "ROLE_DOCTOR";
		List<UserEntity> listOfDocs = new ArrayList<>();
		listOfDocs.add(user1);
		listOfDocs.add(user2);
		
		when(userRepo.getByRole(role)).thenReturn(listOfDocs);
		String[] docs = userService.getDoctorsName(role);
		
		assertEquals(2, docs.length);
		assertNotNull(docs);
	}
	
	@Test
	void givenUser_WhenGetAuthenticated_Validate() {
		UserEntity loggedInUser = user2;
		List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
		simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_DOCTOR"));
		authentication = new UsernamePasswordAuthenticationToken(user2.getUsername(), null,
				simpleGrantedAuthorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		when(userRepo.findByUsername(user2.getUsername())).thenReturn(loggedInUser);
		
		UserEntity user = userService.getAuthenticatedUser();
		assertNotNull(user);
	}
	
	@Test
	void givenUsername_WhenGetDoctor_ThenReturn() {
		String username = "doctorOne";
		UserEntity doctor = user2;
		
		when(userRepo.findDoctorByUsername(username)).thenReturn(doctor);
		
		UserEntity doc = userService.getDoctorByUsername(username);
		
		assertNotNull(doc);
		assertEquals("doctorOne", doc.getUsername());
	}
}
