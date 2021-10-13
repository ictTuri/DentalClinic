package com.clinic.dental.model.user.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.exceptions.CustomMessageException;
import com.clinic.dental.exceptions.DataIdNotFoundException;
import com.clinic.dental.model.appointment.converter.AppointmentConverter;
import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.converter.UserConverter;
import com.clinic.dental.model.user.dto.CustomResponseDto;
import com.clinic.dental.model.user.dto.UserClinicDataDto;
import com.clinic.dental.model.user.dto.UserDto;
import com.clinic.dental.model.user.dto.UserRegisterDto;
import com.clinic.dental.model.user.enums.Role;
import com.clinic.dental.model.user.repository.UserRepository;
import com.clinic.dental.security.auth.RegexPatterns;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final AppointmentRepository appointmentRepo;
	private final PasswordEncoder passEncoder;

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

	@Override
	@Transactional
	public UserDto createUser(@Valid UserDto userDto) {
		if (!existUserByUsername(userDto.getUsername())) {
			if (!existUserByNID(userDto.getNID())) {
				if (!existUserByPhone(userDto.getPhone())) {
					if (!existUserByEmail(userDto.getEmail())) {
						userDto.setPassword(passEncoder.encode(userDto.getPassword()));
						return UserConverter.toDto(userRepo.save(UserConverter.toEntity(userDto)));
					}
					throw new CustomMessageException("Email Already exist!");
				}
				throw new CustomMessageException("Phone Number Already exist!");
			}
			throw new CustomMessageException("NID Already exist!");
		}
		throw new CustomMessageException("Username Already exist!");
	}

	private boolean existUserByEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	private boolean existUserByPhone(String phone) {
		return userRepo.existsByPhone(phone);
	}

	private boolean existUserByNID(String nid) {
		return userRepo.existsByNID(nid);
	}

	private boolean existUserByUsername(String username) {
		return userRepo.existsByUsername(username);
	}

	@Override
	@Transactional
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

	@Override
	@Transactional
	public Void deleteUserById(Long id) {
		userRepo.deleteById(id);
		return null;
	}

	@Override
	public String[] getDoctorsName(String role) {
		List<String> doctors = new ArrayList<>();
		userRepo.getByRole(role).stream().forEach(user -> {
			doctors.add(user.getUsername());
		});
		String[] returnNames = doctors.toArray(new String[0]);
		return returnNames;
	}

	@Override
	@Transactional
	public CustomResponseDto addClient(@Valid UserRegisterDto dto) {
		if (!existUserByUsername(dto.getUsername())) {
			if (!existUserByNID(dto.getNID())) {
				if (!existUserByPhone(dto.getPhone())) {
					if (!existUserByEmail(dto.getEmail())) {
						dto.setPassword(passEncoder.encode(dto.getPassword()));
						userRepo.save(UserConverter.toClientRegisterEntity(dto));
						return new CustomResponseDto("Successful Registration, please login now! ", LocalDateTime.now());
					}
					throw new CustomMessageException("Email Already exist!");
				}
				throw new CustomMessageException("Phone Number Already exist!");
			}
			throw new CustomMessageException("NID Already exist!");
		}
		throw new CustomMessageException("Username Already exist!");
	}

	@Override
	public UserEntity getAuthenticatedUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepo.findByUsername(username);
	}

	@Override
	public UserEntity getDoctorByUsername(String doctorUsername) {
		UserEntity doctor = userRepo.findDoctorByUsername(doctorUsername);
		if(doctor != null) {
			return doctor;
		}
		throw new DataIdNotFoundException("Can not find doctor by given username: "+ doctorUsername);
	}

	@Override
	public UserClinicDataDto getUserDataByCredentials(@NotBlank String credentials) {
		if(credentials.trim().toUpperCase().matches(RegexPatterns.NID)) {
			var userEntity = userRepo.findByNID(credentials);
			return loadUserData(userEntity);
		}else if(credentials.trim().matches(RegexPatterns.PHONE_NUMBER)) {
			var userEntity = userRepo.findByPhone(credentials);
			return loadUserData(userEntity);
		}else {
			var userEntity = userRepo.findByEmail(credentials);
			return loadUserData(userEntity);
		}
	}
	
	private UserClinicDataDto loadUserData(UserEntity user) {
		UserClinicDataDto userToReturn = new UserClinicDataDto();
		userToReturn.setFirstName(user.getFirstName());
		userToReturn.setLastName(user.getLastName());
		userToReturn.setEmail(user.getEmail());
		userToReturn.setPhone(user.getPhone());
		userToReturn.setAge(user.getAge());
		userToReturn.setNID(user.getNID());
		List<DisplayAppointmentDto> appointments = appointmentRepo.findByPatient(user)
				.stream()
				.map(AppointmentConverter::toDto)
				.collect(Collectors.toList());
		userToReturn.setAppointments(appointments);
		return userToReturn;
	}
	
}
