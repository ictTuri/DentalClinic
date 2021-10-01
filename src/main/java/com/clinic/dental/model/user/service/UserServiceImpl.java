package com.clinic.dental.model.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.converter.UserConverter;
import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.enums.Role;
import com.clinic.dental.model.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

	private final UserRepository userRepo;
	
	@Override
	public List<UserDto> getAllUsers() {
		List<UserDto> users = new ArrayList<>();
		userRepo.findAll().stream().forEach(user -> users.add(UserConverter.toDto(user)));
		return users;
	}

	@Override
	public UserDto getUserById(Long id) {
		UserDto dto = UserConverter.toDto(userRepo.locateById(id));
		return dto;
	}

	@Override @Transactional
	public UserDto createUser(@Valid UserDto userDto) {
		return UserConverter.toDto(userRepo.save(UserConverter.toEntity(userDto)));
	}

	@Override @Transactional
	public UserDto updateUserById(@Valid UserDto userDto, Long id) {
		UserEntity user = userRepo.locateById(id);
		user.setAge(userDto.getAge());
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setPhone(userDto.getPhone());
		user.setNID(userDto.getNID());
		user.setRole(Role.valueOf(userDto.getRole().trim().toUpperCase()));
		userRepo.save(user);
		return UserConverter.toDto(user);
	}

	@Override @Transactional
	public Void deleteUserById(Long id) {
		userRepo.deleteById(id);
		return null;
	}

	@Override
	public List<String> getDoctorsName(String role) {
		List<String> doctors = new ArrayList<>();
		userRepo.getByRole(role).stream().forEach(user -> {
			doctors.add(user.getFirstName().concat(" "+user.getLastName()));
		});
		return doctors;
	}

}
