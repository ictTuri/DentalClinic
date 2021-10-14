package com.clinic.dental.util.user;

import java.time.LocalDateTime;

import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.enums.Gender;
import com.clinic.dental.model.user.enums.Role;

public class UserUtilTest {
	public static UserEntity publicOne() {
		UserEntity user = new UserEntity();
		user.setId(1L);
		user.setFirstName("ClientOne");
		user.setLastName("ClientOne");
		user.setUsername("clientone");
		user.setEmail("clientOne@gmail.com");
		user.setNID("C00000001C");
		user.setRole(Role.ROLE_PUBLIC);
		user.setGender(Gender.FEMALE);
		user.setActive(true);
		user.setAge(32);
		user.setPhone("0970000000");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity publicTwo() {
		UserEntity user = new UserEntity();
		user.setId(2L);
		user.setFirstName("ClientTwo");
		user.setLastName("ClientTwo");
		user.setUsername("clienttwo");
		user.setEmail("clientTwo@gmail.com");
		user.setNID("C00000002C");
		user.setRole(Role.ROLE_PUBLIC);
		user.setGender(Gender.MALE);
		user.setActive(false);
		user.setAge(30);
		user.setPhone("0970000001");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity secretaryOne() {
		UserEntity user = new UserEntity();
		user.setId(3L);
		user.setFirstName("SecretaryOne");
		user.setLastName("SecretaryOne");
		user.setUsername("secretaryone");
		user.setEmail("secretaryOne@gmail.com");
		user.setNID("S00000001S");
		user.setRole(Role.ROLE_SECRETARY);
		user.setGender(Gender.MALE);
		user.setActive(true);
		user.setAge(30);
		user.setPhone("0970000003");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity secretaryTwo() {
		UserEntity user = new UserEntity();
		user.setId(2L);
		user.setFirstName("SecretaryTwo");
		user.setLastName("SecretaryTwo");
		user.setUsername("secretarytwo");
		user.setEmail("secretaryTwo@gmail.com");
		user.setNID("S00000002S");
		user.setRole(Role.ROLE_SECRETARY);
		user.setGender(Gender.FEMALE);
		user.setActive(true);
		user.setAge(25);
		user.setPhone("0970000004");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity doctorOne() {
		UserEntity user = new UserEntity();
		user.setId(2L);
		user.setFirstName("DoctorOne");
		user.setLastName("DoctorOne");
		user.setUsername("doctorOne");
		user.setEmail("doctorOne@gmail.com");
		user.setNID("D00000001D");
		user.setRole(Role.ROLE_DOCTOR);
		user.setGender(Gender.MALE);
		user.setActive(true);
		user.setAge(25);
		user.setPhone("0970000005");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity doctorTwo() {
		UserEntity user = new UserEntity();
		user.setId(2L);
		user.setFirstName("DoctorTwo");
		user.setLastName("DoctorTwo");
		user.setUsername("doctorTwo");
		user.setEmail("doctorTwo@gmail.com");
		user.setNID("D00000002D");
		user.setRole(Role.ROLE_DOCTOR);
		user.setGender(Gender.FEMALE);
		user.setActive(true);
		user.setAge(25);
		user.setPhone("0970000006");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity general() {
		UserEntity user = new UserEntity();
		user.setId(null);
		user.setFirstName("general");
		user.setLastName("general");
		user.setUsername("general");
		user.setEmail("general@gmail.com");
		user.setNID("G00000000G");
		user.setRole(Role.ROLE_DOCTOR);
		user.setGender(Gender.FEMALE);
		user.setActive(true);
		user.setAge(25);
		user.setPhone("0970000009");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity generalOne() {
		UserEntity user = new UserEntity();
		user.setId(null);
		user.setFirstName("generalOne");
		user.setLastName("generalOne");
		user.setUsername("generalOne");
		user.setEmail("generalOne@gmail.com");
		user.setNID("G00000001G");
		user.setRole(Role.ROLE_ADMIN);
		user.setGender(Gender.FEMALE);
		user.setActive(true);
		user.setAge(25);
		user.setPhone("0970000010");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
	
	public static UserEntity generalTwo() {
		UserEntity user = new UserEntity();
		user.setId(null);
		user.setFirstName("generalTwo");
		user.setLastName("generalTwo");
		user.setUsername("generalTwo");
		user.setEmail("generalTwo@gmail.com");
		user.setNID("G00000002G");
		user.setRole(Role.ROLE_SECRETARY);
		user.setGender(Gender.MALE);
		user.setActive(true);
		user.setAge(25);
		user.setPhone("0970000019");
		user.setCreatedAt(LocalDateTime.now());
		return user;
	}
}
