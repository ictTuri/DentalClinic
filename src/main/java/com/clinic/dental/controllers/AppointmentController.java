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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentDentistDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.dto.TimeSlotDto;
import com.clinic.dental.model.appointment.service.AppointmentService;
import com.clinic.dental.model.feedback.dto.CreateFeedbackDto;
import com.clinic.dental.model.user.UserEntity;
import com.clinic.dental.model.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/visit")
public class AppointmentController {

	private final AppointmentService appointmentService;
	private final UserService userService;

	
	@GetMapping("/free-schedule")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY','ROLE_PUBLIC')")
	public ResponseEntity<List<SlotDto>> getFreeTimes(){
		return new ResponseEntity<List<SlotDto>>(appointmentService.getFreeTimes(),HttpStatus.OK);
	}
	
	@GetMapping("/free-schedule/doctor")
	@PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
	public ResponseEntity<List<TimeSlotDto>> getDoctorFreeTimes(){
		UserEntity thisUser = userService.getAuthenticatedUser();
		return new ResponseEntity<List<TimeSlotDto>>(appointmentService.getDoctorFreeTimes(thisUser),HttpStatus.OK);
	}
	
	@GetMapping("/doctor/approved")
	@PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
	public ResponseEntity<List<DisplayAppointmentDto>> getDoctorApprovedAppointments(){
		UserEntity thisUser = userService.getAuthenticatedUser();
		return new ResponseEntity<List<DisplayAppointmentDto>>(appointmentService.getDoctorApprovedAppointments(thisUser),HttpStatus.OK);
	}
	
	@GetMapping("/doctor/cancelled")
	@PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
	public ResponseEntity<List<DisplayAppointmentDto>> getDoctorCancelledAppointments(){
		UserEntity thisUser = userService.getAuthenticatedUser();
		return new ResponseEntity<List<DisplayAppointmentDto>>(appointmentService.getDoctorCancelledAppointments(thisUser),HttpStatus.OK);
	}
	
	@PostMapping("/rezerve")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PUBLIC')")
	public ResponseEntity<DisplayAppointmentDto> rezerveAppointment(@Valid @RequestBody CreatePublicAppointmentDto rezerveDto){
		UserEntity thisUser = userService.getAuthenticatedUser();
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.rezerveAppointment(rezerveDto,thisUser),HttpStatus.CREATED);
	}
	
	@GetMapping()
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PUBLIC','ROLE_DOCTOR','ROLE_SECRETARY')")
	public ResponseEntity<List<DisplayAppointmentDto>> getMyAllAppointments(@RequestParam(name = "status", required = false) String status){
		UserEntity thisUser = userService.getAuthenticatedUser();
		return new ResponseEntity<List<DisplayAppointmentDto>>(appointmentService.getMyAllAppointments(status,thisUser),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PUBLIC','ROLE_DOCTOR','ROLE_SECRETARY')")
	public ResponseEntity<DisplayAppointmentDto> getAppointmentById(@PathVariable("id") Long id){
		UserEntity thisUser = userService.getAuthenticatedUser();
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.getAppointmentById(id,thisUser),HttpStatus.OK);
	}
	
	@PostMapping("{id}/feedback")
	@PreAuthorize("hasAnyRole('ROLE_DOCTOR')")
	public ResponseEntity<DisplayAppointmentDto> setAppointmentFeedback(@PathVariable("id") Long id, @RequestBody @Valid CreateFeedbackDto dto){
		UserEntity thisUser = userService.getAuthenticatedUser();
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.setAppointmentFeedback(id,dto,thisUser),HttpStatus.CREATED);
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
	public ResponseEntity<DisplayAppointmentDto> changeAppointmentTimeById(@PathVariable("id") Long id,@Valid @RequestBody ChangeAppointmentTimeDto dto){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.changeAppointmentTimeById(id,dto),HttpStatus.OK);
	}
	
	@PostMapping("{id}/change-dentist")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<DisplayAppointmentDto> changeAppointmentDentist(@PathVariable("id") Long id,@Valid @RequestBody ChangeAppointmentDentistDto dto){
		return new ResponseEntity<DisplayAppointmentDto>(appointmentService.changeAppointmentDentist(id,dto),HttpStatus.OK);
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

}
