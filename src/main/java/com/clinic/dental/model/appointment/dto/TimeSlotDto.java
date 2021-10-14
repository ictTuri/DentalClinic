package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSlotDto {
	private LocalDate date;
	private LocalTime visitStart;
	private LocalTime visitEnd;
}
