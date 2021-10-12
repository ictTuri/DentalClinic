package com.clinic.dental.model.report.service;

import java.util.List;

import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;

public interface ReportService {

TotalRezervationsReportDto getMonthlyReports(int year,int month);

List<TotalRezervationsReportDto> getTotalReportThisYear(int year);

}
