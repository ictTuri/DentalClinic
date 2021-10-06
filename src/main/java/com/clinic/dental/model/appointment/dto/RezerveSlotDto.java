package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class RezerveSlotDto {
	
	private LocalDate day;
	private LocalTime startTime;
	private String doctorName;

}
