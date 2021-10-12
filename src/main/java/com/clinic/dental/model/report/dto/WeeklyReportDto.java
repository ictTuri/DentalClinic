package com.clinic.dental.model.report.dto;

import lombok.Data;

@Data
public class WeeklyReportDto {
	private Integer weekDay;
	private Long total;
	private Long done;
	private Long clientCancelled;
	private Long doctocCancelled;
}
