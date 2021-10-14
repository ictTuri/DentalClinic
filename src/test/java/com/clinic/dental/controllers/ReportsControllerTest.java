package com.clinic.dental.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;
import com.clinic.dental.model.report.service.ReportService;

@ExtendWith(MockitoExtension.class)
class ReportsControllerTest {
	@InjectMocks
	ReportsController reportsController;
	@Mock
	ReportService reportService;
	
	@BeforeEach
	void setup() {
		reportsController = new ReportsController(reportService);
	}
	
	@Test
	void givenEmptyDto_WhenGetReport_ThanValidateStatus() {
		TotalRezervationsReportDto reportsList = new TotalRezervationsReportDto();
		
		when(reportService.getMonthlyReports(2021, 11)).thenReturn(reportsList);
		
		ResponseEntity<TotalRezervationsReportDto> report = reportsController.getMonthlyReports(11);
		assertNotNull(report);
		assertEquals(HttpStatus.OK, report.getStatusCode());
	}
	
	@Test
	void givenEmptyList_WhenGetReport_ThanValidateStatus() {
		List<TotalRezervationsReportDto> reportsList = new ArrayList<>();
		
		when(reportService.getTotalReportThisYear(2021)).thenReturn(reportsList);
		
		ResponseEntity<List<TotalRezervationsReportDto>> reports = reportsController.getTotalReportThisYear();
		assertNotNull(reports);
		assertEquals(HttpStatus.OK, reports.getStatusCode());
	}

}
