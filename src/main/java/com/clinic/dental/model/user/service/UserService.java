package com.clinic.dental.model.user.service;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.dto.CustomResponseDto;
import com.clinic.dental.model.user.dto.UserClinicDataDto;
import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.dto.UserRegisterDto;

public interface UserService {

	List<UserDto> getAllUsers();

	UserDto getUserById(Long id);

	UserDto createUser(@Valid UserDto userDto);

	UserDto updateUserById(@Valid UserDto userDto, Long id);

	Void deleteUserById(Long id);

	String[] getDoctorsName(String role);

	CustomResponseDto addClient(@Valid UserRegisterDto dto);
	
	UserEntity getAuthenticatedUser();

	UserEntity getDoctorByUsername(String doctorUsername);

	UserClinicDataDto getUserDataByCredentials(@NotBlank String credentials);

	UserDto userProfile();

	UserDto updateProfile(@Valid UserDto userDto);

	String getRole();

}
