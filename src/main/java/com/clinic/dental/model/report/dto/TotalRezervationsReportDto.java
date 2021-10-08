package com.clinic.dental.model.report.dto;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Immutable
@Getter
@AllArgsConstructor
public class TotalRezervationsReportDto {
	 private Long total;
	 private String monthName;
	 private Long done;
	 private Long clientCancelled;
	 private Long doctocCancelled;
}
