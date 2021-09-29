package com.clinic.dental.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.dto.AppointmentDto;
import com.clinic.dental.services.AppointmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/visit")
public class AppointmentController {

	private final AppointmentService appointmentService;
	
	@GetMapping
	public ResponseEntity<List<AppointmentDto>> getAllAppointments(){
		return new ResponseEntity<List<AppointmentDto>>(appointmentService.getAllAppointments(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable("id") Long id){
		return new ResponseEntity<AppointmentDto>(appointmentService.getAppointmentById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentDto appointmentDto){
		return new ResponseEntity<AppointmentDto>(appointmentService.createAppointment(appointmentDto),HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<AppointmentDto> updateAppointmentById(@Valid @RequestBody AppointmentDto appointmentDto, @PathVariable("id") Long id){
		return new ResponseEntity<AppointmentDto>(appointmentService.updateAppointmentById(appointmentDto,id),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAppointmentById(@PathVariable("id") Long id){
		return new ResponseEntity<Void>(appointmentService.deleteAppointmentById(id),HttpStatus.NO_CONTENT);
	}
}
