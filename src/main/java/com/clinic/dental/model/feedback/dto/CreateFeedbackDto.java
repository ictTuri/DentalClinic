package com.clinic.dental.model.feedback.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class CreateFeedbackDto {

	@NotBlank(message = "Please fill a description!")
	private String Description;
}
