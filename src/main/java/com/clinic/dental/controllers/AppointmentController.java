package com.clinic.dental.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.RezerveSlotDto;
import com.clinic.dental.model.appointment.dto.ChangeTimeAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.service.AppointmentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/visit")
public class AppointmentController {

	private final AppointmentService appointmentService;
	
	@GetMapping("/free-schedule")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PUBLIC')")
	public ResponseEntity<List<SlotDto>> getFreeTimes(){
		return new ResponseEntity<List<SlotDto>>(appointmentService.getFreeTimes(),HttpStatus.OK);
	}
	
	@PostMapping("/rezerve")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PUBLIC')")
	public ResponseEntity<DisplayAppointmentDto> rezerveAppointment(@Valid @RequestBody RezerveSlotDto rezerveDto){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.rezerveAppointment(rezerveDto),HttpStatus.CREATED);
	}
	
	@GetMapping()
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PUBLIC','ROLE_DOCTOR','ROLE_SECRETARY')")
	public ResponseEntity<List<DisplayAppointmentDto>> getMyAllAppointments(){
		return new ResponseEntity<List<DisplayAppointmentDto>>(appointmentService.getMyAllAppointments(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PUBLIC','ROLE_DOCTOR','ROLE_SECRETARY')")
	public ResponseEntity<DisplayAppointmentDto> getAppointmentById(@PathVariable("id") Long id){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.getAppointmentById(id),HttpStatus.OK);
	}
	
	@PostMapping("{id}/approve")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<DisplayAppointmentDto> approveAppointmentById(@PathVariable("id") Long id){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.approveAppointmentById(id),HttpStatus.OK);
	}
	
	@PostMapping("{id}/close")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<DisplayAppointmentDto> closeAppointmentById(@PathVariable("id") Long id){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.closeAppointmentById(id),HttpStatus.OK);
	}
	
	@PostMapping("{id}/change-time")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<DisplayAppointmentDto> changeAppointmentTimeById(@PathVariable("id") Long id,@Valid @RequestBody ChangeTimeAppointmentDto dto){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.changeAppointmentTimeById(id,dto),HttpStatus.OK);
	}
	
	@PostMapping("/{id}/approve-new-time")
	@PreAuthorize("hasAnyRole('ROLE_PUBLIC')")
	public ResponseEntity<DisplayAppointmentDto> approveNewTimeAppointment(@PathVariable("id") Long id){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.approveNewTimeAppointment(id),HttpStatus.OK);
	}
	
	@PostMapping("/{id}/refuze-new-time")
	@PreAuthorize("hasAnyRole('ROLE_PUBLIC')")
	public ResponseEntity<DisplayAppointmentDto> refuzeNewTimeAppointment(@PathVariable("id") Long id){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.refuzeNewTimeAppointment(id),HttpStatus.OK);
	}
	
	@PostMapping("/{id}/cancel")
	@PreAuthorize("hasAnyRole('ROLE_PUBLIC','ROLE_DOCTOR')")
	public ResponseEntity<DisplayAppointmentDto> cancelAppointment(@PathVariable("id") Long id){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.cancelAppointment(id),HttpStatus.OK);
	}
	
//	@GetMapping
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
//	public ResponseEntity<List<DisplayAppointmentDto>> getAllAppointments(){
//		return new ResponseEntity<List<DisplayAppointmentDto>>(appointmentService.getAllAppointments(),HttpStatus.OK);
//	}
//	
//	@PostMapping
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
//	public ResponseEntity<CreatePublicAppointmentDto> createAppointment(@Valid @RequestBody CreatePublicAppointmentDto appointmentDto){
//		return new ResponseEntity<CreatePublicAppointmentDto>(appointmentService.createAppointment(appointmentDto),HttpStatus.CREATED);
//	}
//	
//	@PutMapping("{id}")
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
//	public ResponseEntity<CreatePublicAppointmentDto> updateAppointmentById(@Valid @RequestBody CreatePublicAppointmentDto appointmentDto, @PathVariable("id") Long id){
//		return new ResponseEntity<CreatePublicAppointmentDto>(appointmentService.updateAppointmentById(appointmentDto,id),HttpStatus.CREATED);
//	}
//	
//	@DeleteMapping("{id}")
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
//	public ResponseEntity<Void> deleteAppointmentById(@PathVariable("id") Long id){
//		return new ResponseEntity<Void>(appointmentService.deleteAppointmentById(id),HttpStatus.NO_CONTENT);
//	}
//
//	@GetMapping("{id}")
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
//	public ResponseEntity<DisplayAppointmentDto> getAppointmentById(@PathVariable("id") Long id){
//		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.getAppointmentById(id),HttpStatus.OK);
//	}
}
