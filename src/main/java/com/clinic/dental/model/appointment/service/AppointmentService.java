package com.clinic.dental.model.appointment.service;

import java.util.List;

import javax.validation.Valid;

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentDentistDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.dto.TimeSlotDto;
import com.clinic.dental.model.feedback.dto.CreateFeedbackDto;
import com.clinic.dental.model.user.UserEntity;

public interface AppointmentService {
	List<AppointmentEntity> getAllAppointments();

	DisplayAppointmentDto getAppointmentById(Long id, UserEntity authenticated);

	List<SlotDto> getFreeTimes();

	DisplayAppointmentDto rezerveAppointment(@Valid CreatePublicAppointmentDto rezerveDto, UserEntity authenticated);

	DisplayAppointmentDto approveAppointmentById(Long id);

	DisplayAppointmentDto changeAppointmentTimeById(Long id, @Valid ChangeAppointmentTimeDto dto);

	DisplayAppointmentDto closeAppointmentById(Long id);

	List<DisplayAppointmentDto> getMyAllAppointments(String status, UserEntity thisUser);

	DisplayAppointmentDto approveNewTimeAppointment(Long id);

	DisplayAppointmentDto refuzeNewTimeAppointment(Long id);

	DisplayAppointmentDto cancelAppointment(Long id, UserEntity thisUser);

	DisplayAppointmentDto changeAppointmentDentist(Long id, @Valid ChangeAppointmentDentistDto dto);

	void setFeedbackAfterEightHoursNull(String defaultFeedback);

	DisplayAppointmentDto setAppointmentFeedback(Long id, @Valid CreateFeedbackDto dto, UserEntity thisUser);

	List<TimeSlotDto> getDoctorFreeTimes(UserEntity thisUser);

	List<DisplayAppointmentDto> getDoctorApprovedAppointments(UserEntity thisUser);

	List<DisplayAppointmentDto> getDoctorCancelledAppointments(UserEntity thisUser);

}
