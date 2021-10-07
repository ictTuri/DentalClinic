package com.clinic.dental.model.original_appointment.converter;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.original_appointment.OriginalAppointmentEntity;

public class OriginalAppointmentConverter {
	private OriginalAppointmentConverter() {
	}
	
	public static OriginalAppointmentEntity toEntity(AppointmentEntity appointment) {
		OriginalAppointmentEntity entity = new OriginalAppointmentEntity();
		entity.setId(null);
		entity.setDay(appointment.getDate());
		entity.setStartTime(appointment.getStartTime());
		entity.setEndTime(appointment.getEndTime());
		return entity;
	}
}
