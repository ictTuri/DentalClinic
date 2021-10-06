package com.clinic.dental.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.model.feedback.dto.DisplayFeedbackDto;
import com.clinic.dental.model.feedback.service.FeedbackService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/visit")
public class FeedbackController {

	private final FeedbackService feedbackService;
	
	@GetMapping("{id}/feedback")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<DisplayFeedbackDto> getAppointmentFeedback(@PathVariable("id") Long id){
		return new ResponseEntity<>(feedbackService.getAppointmentFeedback(id),HttpStatus.OK);
	}
	
	
}
