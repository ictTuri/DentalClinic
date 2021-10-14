package com.clinic.dental.model.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.clinic.dental.model.user.enums.Gender;
import com.clinic.dental.model.user.enums.Role;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	
	private String firstName;
	private String lastName;
	private String username;
	private Integer age;
	private String email;
	private String password;
	@Column(name = "phone_number")
	private String phone;
	@Column(name = "national_id")
	private String NID;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	@Enumerated(EnumType.STRING)
	private Role role;
	private boolean isActive;
	private LocalDateTime createdAt;
}
