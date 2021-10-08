package com.clinic.dental.model.appointment.converter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.validation.Valid;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.enums.AppointmentType;
import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.user.UserEntity;

public class AppointmentConverter {

	private AppointmentConverter() {}
	
	public static DisplayAppointmentDto toDto(AppointmentEntity appointment) {
		DisplayAppointmentDto dto = new DisplayAppointmentDto();
		dto.setId(appointment.getId());
		dto.setDate(appointment.getDate());
		dto.setStartTime(appointment.getStartTime());
		dto.setEndTime(appointment.getEndTime());
		dto.setDentist(appointment.getDentist());
		dto.setPatient(appointment.getPatient().getFirstName().concat(" "+appointment.getPatient().getLastName()));
		dto.setType(appointment.getType().toString());
		dto.setStatus(appointment.getStatus().toString());
		return dto;
	}

	public static CreatePublicAppointmentDto toDisplayDto(AppointmentEntity entity) {
		CreatePublicAppointmentDto dto = new CreatePublicAppointmentDto();
		dto.setDate(entity.getDate());
		dto.setStartTime(entity.getStartTime());
		dto.setDentist(entity.getDentist());
		dto.setType(entity.getType().toString());
		return dto;
	}

	public static AppointmentEntity rezervationToEntity(@Valid CreatePublicAppointmentDto rezerveDto, UserEntity user,String doctorUsername) {
		AppointmentEntity entity = new AppointmentEntity();
		entity.setId(null);
		entity.setCreatedAt(LocalDateTime.now());
		entity.setDate(rezerveDto.getDate());
		entity.setStartTime(rezerveDto.getStartTime().truncatedTo(ChronoUnit.HOURS));
		entity.setEndTime(rezerveDto.getStartTime().plusHours(1).truncatedTo(ChronoUnit.HOURS));
		entity.setDentist(doctorUsername);
		entity.setPatient(user);
		entity.setType(AppointmentType.valueOf(rezerveDto.getType().toUpperCase()));
		entity.setStatus(Status.APPENDING);
		entity.setLastUpdatedAt(LocalDateTime.now());
		entity.setFeedback(null);
		return entity;
	}

}
