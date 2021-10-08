package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SlotDto {
	private LocalDate date;
	private LocalTime visitStart;
	private LocalTime visitEnd;
	private List<String> doctors = new ArrayList<>();
}
