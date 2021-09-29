package com.clinic.dental.model.user.service;

import java.util.List;

import javax.validation.Valid;

import com.clinic.dental.model.user.dto.UserDto;

public interface UserService {

	List<UserDto> getAllUsers();

	UserDto getUserById(Long id);

	UserDto createUser(@Valid UserDto userDto);

	UserDto updateUserById(@Valid UserDto userDto, Long id);

	Void deleteUserById(Long id);

}
