package com.clinic.dental.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.clinic.dental.enums.AppointmentType;

import lombok.Data;

@Data
@Entity
@Table(name = "appointments")
public class AppointmentEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appointment_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity patient;
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity dentist;
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
	private AppointmentType type;
	
}
