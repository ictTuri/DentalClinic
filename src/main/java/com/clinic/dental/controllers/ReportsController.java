package com.clinic.dental.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.report.dto.DentistsTotalReportsDto;
import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;
import com.clinic.dental.model.report.service.ReportService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@CrossOrigin(origins = "https://dental-clinic7.web.app",allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/reports")
@Validated
public class ReportsController {

	private final ReportService reportService;
	
	@GetMapping("/total")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<List<TotalRezervationsReportDto>> getTotalReportThisYear(){
		int year = LocalDate.now().getYear();
		return new ResponseEntity<>(reportService.getTotalReportThisYear(year),HttpStatus.OK);
	}
	
	@GetMapping("{month}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<TotalRezervationsReportDto> getMonthlyReports(@PathVariable("month") @NotNull @Min(1) @Max(12) int month){
		int year = LocalDate.now().getYear();
		return new ResponseEntity<>(reportService.getMonthlyReports(year,month),HttpStatus.OK);
	}
	
	@GetMapping("dentists-report")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<List<DentistsTotalReportsDto>> getDentistsTotalReports(){
		int year = LocalDate.now().getYear();
		return new ResponseEntity<>(reportService.getDentistsTotalReports(year),HttpStatus.OK);
	}
	
	@GetMapping("dentists-report/{month}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<DentistsTotalReportsDto> getDentistsMonthlyReports(@PathVariable("month") @NotNull @Min(1) @Max(12) int month){
		int year = LocalDate.now().getYear();
		return new ResponseEntity<>(reportService.getDentistsMonthlyReports(year,month,Status.DONE),HttpStatus.OK);
	}
	
	@GetMapping("dentists-cancelled-report")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<List<DentistsTotalReportsDto>> getDentistsTotalCancellReports(){
		int year = LocalDate.now().getYear();
		return new ResponseEntity<>(reportService.getDentistsTotalCancellReports(year),HttpStatus.OK);
	}
	
	@GetMapping("dentists-cancelled-report/{month}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_SECRETARY')")
	public ResponseEntity<DentistsTotalReportsDto> getDentistsMonthlyCancellReports(@PathVariable("month") @NotNull @Min(1) @Max(12) int month){
		int year = LocalDate.now().getYear();
		return new ResponseEntity<>(reportService.getDentistsMonthlyCancellReports(year,month,Status.DOCTOR_CANCELLED),HttpStatus.OK);
	}
}
