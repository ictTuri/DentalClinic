package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public class CreatePublicAppointmentDto {
	
	private String dentist;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private String type;

}
