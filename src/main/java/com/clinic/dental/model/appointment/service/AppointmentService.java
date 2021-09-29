package com.clinic.dental.model.appointment.service;

import java.util.List;

import javax.validation.Valid;

import com.clinic.dental.model.appointment.dto.AppointmentDto;

public interface AppointmentService {

	List<AppointmentDto> getAllAppointments();

	AppointmentDto getAppointmentById(Long id);
	
	AppointmentDto createAppointment(@Valid AppointmentDto appointmentDto);

	AppointmentDto updateAppointmentById(@Valid AppointmentDto appointmentDto, Long id);

	Void deleteAppointmentById(Long id);

}
