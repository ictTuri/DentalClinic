package com.clinic.dental.model.report.service;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.report.dto.DentistsTotalReportsDto;
import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;

public interface ReportService {

TotalRezervationsReportDto getMonthlyReports(int year,int month);

List<TotalRezervationsReportDto> getTotalReportThisYear(int year);

DentistsTotalReportsDto getDentistsMonthlyReports(int year, @NotNull @Min(1) @Max(12) int month, Status status);

List<DentistsTotalReportsDto> getDentistsTotalReports(int year);

List<DentistsTotalReportsDto> getDentistsTotalCancellReports(int year);

DentistsTotalReportsDto getDentistsMonthlyCancellReports(int year, @NotNull @Min(1) @Max(12) int month, Status status);

}
