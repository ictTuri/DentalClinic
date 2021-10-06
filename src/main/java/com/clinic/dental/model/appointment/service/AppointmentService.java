package com.clinic.dental.model.appointment.service;

import java.util.List;

import javax.validation.Valid;

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.RezerveSlotDto;
import com.clinic.dental.model.appointment.dto.ChangeTimeAppointmentDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;

public interface AppointmentService {

	List<DisplayAppointmentDto> getAllAppointments();

	DisplayAppointmentDto getAppointmentById(Long id);
	
	CreatePublicAppointmentDto createAppointment(@Valid CreatePublicAppointmentDto appointmentDto);

	CreatePublicAppointmentDto updateAppointmentById(@Valid CreatePublicAppointmentDto appointmentDto, Long id);

	Void deleteAppointmentById(Long id);

	List<SlotDto> getFreeTimes();

	DisplayAppointmentDto rezerveAppointment(@Valid RezerveSlotDto rezerveDto);

	DisplayAppointmentDto approveAppointmentById(Long id);

	DisplayAppointmentDto changeAppointmentTimeById(Long id, @Valid ChangeTimeAppointmentDto dto);

	DisplayAppointmentDto closeAppointmentById(Long id);

	List<DisplayAppointmentDto> getMyAllAppointments();

}
