package com.clinic.dental.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;

import com.clinic.dental.enums.Gender;
import com.clinic.dental.enums.Role;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(name = "user_id")
	private String id;
	
	private String firstName;
	private String lastName;
	private Integer age;
	private String email;
	private String password;
	@Column(name = "phone_number")
	private String phone;
	@Column(name = "national_id")
	private String NID;
	private Gender gender;
	private Role role;
}
