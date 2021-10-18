package com.clinic.dental.model.report.service;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.report.dto.DentistsTotalReportsDto;
import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;
import com.clinic.dental.model.report.service.impl.ReportServiceImpl;

@SpringBootTest
@Transactional
class ReportServiceTest {
	@InjectMocks
	ReportServiceImpl reportService;
	@Mock
	AppointmentRepository appRepo;
	@Test
	void whenGetMonth_ValidateExecution() {
		TotalRezervationsReportDto dto = reportService.getMonthlyReports(2021, 10);
		assertNotNull(dto);
	}

	@Test
	void givenMonth_ThenGetMonthName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = ReportServiceImpl.class.getDeclaredMethod("getMonth", int.class,Locale.class);
		method.setAccessible(true);
		int month = 8;
		String monthName = (String) method.invoke(reportService, month,Locale.US);
		assertEquals("August", monthName);
	}
	
	@Test
	void whenGetWeekSlots_Validate() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = ReportServiceImpl.class.getDeclaredMethod("weekSlots", LocalDate.class,LocalDate.class);
		method.setAccessible(true);
		LocalDate start = LocalDate.now();
		LocalDate end = start.plusMonths(1);
		@SuppressWarnings("unchecked")
		List<LocalDate> weeks = (List<LocalDate>) method.invoke(reportService, start, end);
		assertNotNull(weeks);
	}
	
	@Test
	void whenGetYearReport_Validate12months() {
		List<TotalRezervationsReportDto> dtos = reportService.getTotalReportThisYear(2021);
		assertEquals(12, dtos.size());
		assertNotNull(dtos);
	}
	
	@Test
	void whenGetDentistMonthlyReport_ValidateExecution() {
		DentistsTotalReportsDto dto = reportService.getDentistsMonthlyReports(2021, 10, Status.DONE);
		assertNotNull(dto);
	}
	
	@Test
	void whenGetDentistTotalReports_ValidateExecution() {
		List<DentistsTotalReportsDto> list = reportService.getDentistsTotalReports(2021);
		assertNotNull(list);
	}
	
	@Test
	void whenGetDentistTotalCancelReports_ValidateExecution() {
		List<DentistsTotalReportsDto> list = reportService.getDentistsTotalCancellReports(2021);
		assertNotNull(list);
	}
	
	@Test
	void whenGetDentistTotalMonthlyCancelReports_ValidateExecution() {
		DentistsTotalReportsDto list = reportService.getDentistsMonthlyCancellReports(2021,11,Status.DOCTOR_CANCELLED);
		assertNotNull(list);
	}
}

