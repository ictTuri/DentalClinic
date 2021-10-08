package com.clinic.dental.util;

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
	
}
