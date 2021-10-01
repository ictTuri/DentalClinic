package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class DisplayAppointmentDto {

	private String dentist;
	private String patient;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String feedback;
	private String type;
}
