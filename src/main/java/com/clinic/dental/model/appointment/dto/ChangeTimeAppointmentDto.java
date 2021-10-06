package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ChangeTimeAppointmentDto {

	@NotNull(message = "Please enter a day of format: 'YYYY-MM-DD'")
	private LocalDate day;
	@NotNull(message = "Please enter a start time of format: 'HH-MM-SS'")
	private LocalTime startTime;
}
