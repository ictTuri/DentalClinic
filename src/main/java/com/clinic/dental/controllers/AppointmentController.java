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

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/visit")
public class AppointmentController {

	private final AppointmentService appointmentService;
	
	@GetMapping("/schedule")
	public ResponseEntity<List<SlotDto>> getFreeTimes(){
		return new ResponseEntity<List<SlotDto>>(appointmentService.getFreeTimes(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<DisplayAppointmentDto>> getAllAppointments(){
		return new ResponseEntity<List<DisplayAppointmentDto>>(appointmentService.getAllAppointments(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<DisplayAppointmentDto> getAppointmentById(@PathVariable("id") Long id){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.getAppointmentById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CreatePublicAppointmentDto> createAppointment(@Valid @RequestBody CreatePublicAppointmentDto appointmentDto){
		return new ResponseEntity<CreatePublicAppointmentDto>(appointmentService.createAppointment(appointmentDto),HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<CreatePublicAppointmentDto> updateAppointmentById(@Valid @RequestBody CreatePublicAppointmentDto appointmentDto, @PathVariable("id") Long id){
		return new ResponseEntity<CreatePublicAppointmentDto>(appointmentService.updateAppointmentById(appointmentDto,id),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteAppointmentById(@PathVariable("id") Long id){
		return new ResponseEntity<Void>(appointmentService.deleteAppointmentById(id),HttpStatus.NO_CONTENT);
	}
}
