package com.clinic.dental.util;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;

public class ChangeTimeAppointmentDtoUtilTest {
	public static ChangeAppointmentTimeDto changeTimeOne() {
		ChangeAppointmentTimeDto dto = new ChangeAppointmentTimeDto();
		dto.setDay(LocalDate.of(2021, 11, 12));
		dto.setStartTime(LocalTime.of(15, 00));
		return dto;
	}
}
