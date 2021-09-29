package com.clinic.dental.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserDto {
	
	@NotBlank(message = "First Name is mandatory!")
	private String firstName;
	@NotBlank(message = "Last Name is mandatory!")
	private String lastName;
	@NotNull(message = "Age is mandatory!")
	private Integer age;
	@NotBlank(message = "Email is mandatory!")
	private String email;
	@NotBlank(message = "password is mandatory!")
	private String password;
	@NotBlank(message = "Phone Number is mandatory!")
	private String phone;
	private String NID;
	private String gender;
	@NotBlank(message = "Role is mandatory!")
	private String role;
}
