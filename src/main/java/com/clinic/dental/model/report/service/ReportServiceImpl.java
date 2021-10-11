package com.clinic.dental.model.report.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {

	@Override
	public TotalRezervationsReportDto getMonthlyReports(LocalDate date) {
		// TODO Auto-generated method stub
		return null;
	}

}
