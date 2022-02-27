package com.clinic.dental.model.address;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class AddressEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id", nullable = false, unique = true)
	private Long id;
	
	private String state;
	private String city;
	private String roadName;
	private String floorNumber;
	private String doorNumber;
	private String zip;
	private String addressTwo;

}
