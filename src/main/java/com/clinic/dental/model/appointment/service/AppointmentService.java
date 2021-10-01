package com.clinic.dental.model.appointment.service;

import java.util.List;

import javax.validation.Valid;

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;

public interface AppointmentService {

	List<DisplayAppointmentDto> getAllAppointments();

	DisplayAppointmentDto getAppointmentById(Long id);
	
	CreatePublicAppointmentDto createAppointment(@Valid CreatePublicAppointmentDto appointmentDto);

	CreatePublicAppointmentDto updateAppointmentById(@Valid CreatePublicAppointmentDto appointmentDto, Long id);

	Void deleteAppointmentById(Long id);

	List<SlotDto> getFreeTimes();

}
