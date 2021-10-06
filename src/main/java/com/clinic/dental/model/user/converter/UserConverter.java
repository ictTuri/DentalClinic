package com.clinic.dental.model.user.converter;

import java.time.LocalDateTime;

import javax.validation.Valid;

import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.dto.UserRegisterDto;
import com.clinic.dental.model.user.enums.Gender;
import com.clinic.dental.model.user.enums.Role;

public class UserConverter {

	UserConverter(){}
	
	public static UserDto toDto(UserEntity user) {
		UserDto dto = new UserDto();
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setUsername(user.getUsername());
		dto.setAge(user.getAge());
		dto.setEmail(user.getEmail());
		dto.setGender(user.getGender().toString());
		dto.setPassword(null);
		dto.setPhone(user.getPhone());
		dto.setNID(user.getNID());
		dto.setRole(user.getRole().toString());
		return dto;
	}
	
	public static UserEntity toEntity(UserDto dto) {
		UserEntity entity = new UserEntity();
		entity.setId(null);
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setUsername(dto.getUsername());
		entity.setAge(dto.getAge());
		entity.setEmail(dto.getEmail());
		entity.setGender(Gender.valueOf(dto.getGender().trim().toUpperCase()));
		entity.setPassword(dto.getPassword());
		entity.setPhone(dto.getPhone());
		entity.setNID(dto.getNID());
		entity.setRole(Role.valueOf(dto.getRole().trim().toUpperCase()));
		entity.setCreatedAt(LocalDateTime.now());
		entity.setActive(true);
		return entity;
	}

	public static UserEntity toClientRegisterEntity(@Valid UserRegisterDto dto) {
		UserEntity entity = new UserEntity();
		entity.setId(null);
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setUsername(dto.getUsername());
		entity.setAge(dto.getAge());
		entity.setEmail(dto.getEmail());
		entity.setPassword(dto.getPassword());
		entity.setNID(dto.getNID());
		entity.setPhone(dto.getPhone());
		entity.setActive(true);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setGender(Gender.valueOf(dto.getGender().trim().toUpperCase()));
		entity.setRole(Role.ROLE_PUBLIC);
		return entity;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
