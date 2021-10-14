package com.clinic.dental.model.report.dto;

import java.util.Map;

import lombok.Data;

@Data
public class WeeklyDentistsTotalReportsDto {
	private Integer weekDay;
	private Map<String, Long> weeklyResult;
}
