package com.clinic.dental.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserDto {

	@NotBlank(message = "First Name is mandatory!")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory!")
	private String lastName;
	
	@NotBlank(message = "Username is mandatory!")
	private String username;

	@NotNull(message = "Age is mandatory!")
	private Integer age;

	@Email(message = "Please enter a valid email address!")
	@NotBlank
	private String email;

	@NotBlank(message = "password is mandatory!")
	private String password;

	@Pattern(regexp = "^06[7-9]{1}[0-9]{7}$", message = "Please enter a valid Albanian phone number")
	private String phone;

	@Pattern(regexp = "^[a-zA-Z]{1}[0-9]{8}[a-zA-Z]{1}$", message = "Please enter a valid NID format \"X12345678Y\"!")
	private String NID;

	@NotBlank(message = "Please enter \"male\" or \"female\"!")
	private String gender;

	@NotBlank(message = "Role is mandatory! Enter \"doctor\" or \"secretary\" or \"public\"!")
	private String role;
}
