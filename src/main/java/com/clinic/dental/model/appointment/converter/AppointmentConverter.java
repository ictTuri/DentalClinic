package com.clinic.dental.model.appointment.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.enums.AppointmentType;

public class AppointmentConverter {

	public AppointmentConverter() {}
	
	public static DisplayAppointmentDto toDto(AppointmentEntity appointment) {
		DisplayAppointmentDto dto = new DisplayAppointmentDto();
		dto.setDate(appointment.getDate());
		dto.setStartTime(appointment.getStartTime());
		dto.setEndTime(appointment.getEndTime());
		dto.setDentist(appointment.getDentist());
//		dto.setPatient(appointment.getPatient().getFirstName().concat(" "+appointment.getPatient().getLastName()));
		dto.setPatient("Static Patient");
		dto.setType(appointment.getType().toString());
		return dto;
	}

	public static AppointmentEntity toEntity(@Valid CreatePublicAppointmentDto appointmentDto) {
		AppointmentEntity entity = new AppointmentEntity();
		entity.setId(null);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setDate(appointmentDto.getDate());
		entity.setStartTime(appointmentDto.getStartTime().truncatedTo(ChronoUnit.HOURS).plusHours(1));
		entity.setEndTime(appointmentDto.getEndTime().truncatedTo(ChronoUnit.HOURS).plusHours(1));
		entity.setDentist(appointmentDto.getDentist());
		// Need to pass here the authenticated user
		entity.setPatient(null);
		entity.setType(AppointmentType.valueOf(appointmentDto.getType().trim().toUpperCase()));
		entity.setLastUpdatedAt(LocalDateTime.now());
		entity.setFeedback(null);
		return entity;
	}

	public static CreatePublicAppointmentDto toDisplayDto(AppointmentEntity entity) {
		CreatePublicAppointmentDto dto = new CreatePublicAppointmentDto();
		dto.setDate(entity.getDate());
		dto.setStartTime(entity.getStartTime());
		dto.setEndTime(entity.getEndTime());
		dto.setDentist(entity.getDentist());
		dto.setType(entity.getType().toString());
		return dto;
	}

}
