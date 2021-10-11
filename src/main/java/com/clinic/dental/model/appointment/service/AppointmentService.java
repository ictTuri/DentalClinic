package com.clinic.dental.model.appointment.service;

import java.util.List;

import javax.validation.Valid;

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentDentistDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;

public interface AppointmentService {

	List<DisplayAppointmentDto> getAllAppointments();

	DisplayAppointmentDto getAppointmentById(Long id);

	Void deleteAppointmentById(Long id);

	List<SlotDto> getFreeTimes();

	DisplayAppointmentDto rezerveAppointment(@Valid CreatePublicAppointmentDto rezerveDto);

	DisplayAppointmentDto approveAppointmentById(Long id);

	DisplayAppointmentDto changeAppointmentTimeById(Long id, @Valid ChangeAppointmentTimeDto dto);

	DisplayAppointmentDto closeAppointmentById(Long id);

	List<DisplayAppointmentDto> getMyAllAppointments(String status);

	DisplayAppointmentDto approveNewTimeAppointment(Long id);

	DisplayAppointmentDto refuzeNewTimeAppointment(Long id);

	DisplayAppointmentDto cancelAppointment(Long id);

	DisplayAppointmentDto changeAppointmentDentist(Long id, @Valid ChangeAppointmentDentistDto dto);

	void setFeedbackAfterEightHoursNull(String defaultFeedback);

}
