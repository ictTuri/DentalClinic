package com.clinic.dental.util;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;

public class RezerveSlotDtoUtilTest {
	public static CreatePublicAppointmentDto rezerveSlot() {
		CreatePublicAppointmentDto dto = new CreatePublicAppointmentDto();
		dto.setDate(LocalDate.of(2021, 11, 2));
		dto.setDentist("dentistname");
		dto.setStartTime(LocalTime.of(15, 00));
		return dto;
	}
}
