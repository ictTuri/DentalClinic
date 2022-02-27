package com.clinic.dental.model.clinic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.clinic.dental.model.address.AddressEntity;
import com.clinic.dental.model.user.UserEntity;

import lombok.Data;

@Data
@Entity
@Table(name = "clinics")
public class ClinicEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clinic_id", nullable = false, unique = true)
	private Long id;
	private String name;
	
	@OneToOne
	@JoinColumn(name = "secretary", referencedColumnName = "user_id", nullable = true)
	private UserEntity secretary;
	
	@OneToOne
	@JoinColumn(name = "address", referencedColumnName = "address_id", nullable = true)
	private AddressEntity address;
	private String phone;
	private String email;
}
