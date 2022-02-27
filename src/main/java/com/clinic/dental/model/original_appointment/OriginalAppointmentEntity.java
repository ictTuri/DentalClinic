package com.clinic.dental.model.original_appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "original_appointment")
public class OriginalAppointmentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "original_appointment_id", nullable = false, unique = true)
	private Long id;
	
	private LocalDate day;
	private LocalTime startTime;
	private LocalTime endTime;
}
