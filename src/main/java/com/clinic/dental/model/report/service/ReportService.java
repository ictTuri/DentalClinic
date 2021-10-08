package com.clinic.dental.model.report.service;

import java.time.LocalDate;
import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;

public interface ReportService {

TotalRezervationsReportDto getMonthlyReports(LocalDate date);

}
