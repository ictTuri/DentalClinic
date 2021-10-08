package com.clinic.dental.util;

import java.time.LocalDate;
import java.time.LocalTime;

import com.clinic.dental.model.appointment.dto.RezerveSlotDto;

public class RezerveSlotDtoUtilTest {
	public static RezerveSlotDto rezerveSlot() {
		RezerveSlotDto dto = new RezerveSlotDto();
		dto.setDay(LocalDate.of(2021, 11, 2));
		dto.setDoctorUsername("dentistname");
		dto.setStartTime(LocalTime.of(15, 00));
		return dto;
	}
}
