package com.clinic.dental.model.report.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class DentistsTotalReportsDto {

	private String monthName; 
	private Map<String, Long> dentistsReport;
	private List<WeeklyDentistsTotalReportsDto> weeklyReport;
	
}
