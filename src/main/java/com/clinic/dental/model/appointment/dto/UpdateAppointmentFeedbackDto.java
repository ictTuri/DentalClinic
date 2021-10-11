package com.clinic.dental.model.appointment.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UpdateAppointmentFeedbackDto {
	
	@NotBlank(message = "FeedBack is mandatory!")
	private String feedback;
}
