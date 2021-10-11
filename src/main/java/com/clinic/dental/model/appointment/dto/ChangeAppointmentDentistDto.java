package com.clinic.dental.model.appointment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangeAppointmentDentistDto {

	@NotBlank(message = "Please enter a dentist username!")
	private String dentist;
}
