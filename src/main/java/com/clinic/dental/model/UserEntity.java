package com.clinic.dental.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

import com.clinic.dental.enums.Gender;
import com.clinic.dental.enums.Role;

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
	private Integer age;
	private String email;
	private String password;
	@Column(name = "phone_number")
	private String phone;
	@Column(name = "national_id")
	private String NID;
	private Gender gender;
	private Role role;
	@OneToMany
	private Set<AppointmentEntity> appointments; 
}
