package com.clinic.dental.util;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clinic.dental.model.appointment.dto.ChangeTimeAppointmentDto;

public class ChangeTimeAppointmentDtoUtilTest {
	public static ChangeTimeAppointmentDto changeTimeOne() {
		ChangeTimeAppointmentDto dto = new ChangeTimeAppointmentDto();
		dto.setDay(LocalDate.of(2021, 11, 12));
		dto.setStartTime(LocalTime.of(15, 00));
		return dto;
	}
}
