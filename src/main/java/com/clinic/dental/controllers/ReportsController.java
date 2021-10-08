package com.clinic.dental.controllers;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;
import com.clinic.dental.model.report.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
public class ReportsController {

	private final ReportService reportService;
	
	@GetMapping("/total")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<TotalRezervationsReportDto> getMonthlyReports(@RequestParam(name = "month", required = false) LocalDate month){
		return new ResponseEntity<>(reportService.getMonthlyReports(month),HttpStatus.OK);
	}
}
