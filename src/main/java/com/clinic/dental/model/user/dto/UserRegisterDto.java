package com.clinic.dental.model.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.clinic.dental.model.user.enums.Gender;
import com.clinic.dental.utils.ValueOfEnum;

import lombok.Data;

@Data
public class UserRegisterDto {

	@NotBlank(message = "First Name is mandatory!")
	private String firstName;
	@NotBlank(message = "First Name is mandatory!")
	private String lastName;
	@NotBlank(message = "First Name is mandatory!")
	private String username;
	@NotNull(message = "First Name is mandatory!")
	private Integer age;
	
	@NotBlank(message = "First Name is mandatory!")
	@Email
	private String email;
	
	@NotBlank(message = "First Name is mandatory!")
	private String password;
	
	@Pattern(regexp = "^06[7-9]{1}[0-9]{7}$", message = "Please enter a valid Albanian phone number")
	@NotNull(message = "Phone is mandatory!")
	private String phone;

	@Pattern(regexp = "^[a-zA-Z]{1}[0-9]{8}[a-zA-Z]{1}$", message = "Please enter a valid NID format \"X12345678Y\"!")
	@NotNull(message = "NID is mandatory!/try 'nid'")
	private String NID;

	@NotBlank(message = "Please enter \"male\" or \"female\"!")
	@ValueOfEnum(enumClass = Gender.class, message = "Please enter \\\"male\\\" or \\\"female\\\"!\"")
	private String gender;

}
