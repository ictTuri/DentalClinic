package com.clinic.dental.model.report.service.impl;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.enums.Status;
import com.clinic.dental.model.appointment.repository.AppointmentRepository;
import com.clinic.dental.model.report.dto.DentistsTotalReportsDto;
import com.clinic.dental.model.report.dto.TotalRezervationsReportDto;
import com.clinic.dental.model.report.dto.WeeklyDentistsTotalReportsDto;
import com.clinic.dental.model.report.dto.WeeklyReportDto;
import com.clinic.dental.model.report.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportServiceImpl implements ReportService {
	
	Logger log = LogManager.getLogger(ReportServiceImpl.class);

	private final AppointmentRepository appointmentRepo;

	@Override
	public TotalRezervationsReportDto getMonthlyReports(int year, int month) {
		TotalRezervationsReportDto dto = new TotalRezervationsReportDto();
		List<AppointmentEntity> allAppointments = new ArrayList<>();
		appointmentRepo.findAllThisYear(year).stream().map(app -> allAppointments.add(app)).collect(Collectors.toList());
		dto.setYear(year);
		String monthName = getMonth(month, Locale.US);
		dto.setMonthName(monthName);

		Long total = allAppointments.stream()
				.filter(app -> app.getCreatedAt().getMonth().equals(LocalDate.of(year, month, 1).getMonth()))
				.filter(app -> app.getCreatedAt().getYear() == year).parallel().count();
		dto.setTotal(total);
		
		Long totalDone = allAppointments.stream()
				.filter(app -> app.getCreatedAt().getMonth().equals(LocalDate.of(year, month, 1).getMonth()) && app.getStatus().equals(Status.DONE))
				.filter(app -> app.getCreatedAt().getYear() == year).parallel().count();
		dto.setDone(totalDone);
		
		Long doctorCancelled = allAppointments.stream()
				.filter(app -> app.getCreatedAt().getMonth().equals(LocalDate.of(year, month, 1).getMonth()) && app.getStatus().equals(Status.DOCTOR_CANCELLED))
				.filter(app -> app.getCreatedAt().getYear() == year).parallel().count();
		dto.setDoctocCancelled(doctorCancelled);
		
		Long clientCancelled = allAppointments.stream()
				.filter(app -> app.getCreatedAt().getMonth().equals(LocalDate.of(year, month, 1).getMonth()) && app.getStatus().equals(Status.USER_CANCELLED))
				.filter(app -> app.getCreatedAt().getYear() == year).parallel().count();
		dto.setClientCancelled(clientCancelled);
		dto.setWeeklyReport(weeklyReport(allAppointments, year, month));

		log.info("Getting monthly report for appointments!");
		return dto;
	}

	private String getMonth(int month, Locale locale) {
		DateFormatSymbols symbols = new DateFormatSymbols(locale);
		String[] monthNames = symbols.getMonths();
		return monthNames[month - 1];
	}
	
	private List<WeeklyReportDto> weeklyReport(List<AppointmentEntity> appointment,int year,int month){
		List<WeeklyReportDto> weeklyList = new ArrayList<>();
		List<LocalDate> weekTimes =  weekSlots(LocalDate.of(year, month, 1), LocalDate.of(year, month, 1).plusMonths(1));
	
		weekTimes.forEach(week -> {
			WeeklyReportDto dto = new WeeklyReportDto();
			Long total = appointment.stream().filter(app -> app.getCreatedAt().toLocalDate().isAfter(week.minusDays(1)))
					.filter(app -> app.getCreatedAt().toLocalDate().isBefore(week.plusDays(7)))
					.filter(app -> app.getCreatedAt().getYear() == year).parallel().count();
			dto.setTotal(total);
			
			Long totalDone = appointment.stream().filter(app -> app.getCreatedAt().toLocalDate().isAfter(week.minusDays(1)))
					.filter(app -> app.getCreatedAt().toLocalDate().isBefore(week.plusDays(7)))
					.filter(app -> app.getCreatedAt().getYear() == year)
					.filter(app -> app.getStatus().equals(Status.DONE)).parallel().count();
			dto.setDone(totalDone);
			
			Long totalDoctorCancelled = appointment.stream().filter(app -> app.getCreatedAt().toLocalDate().isAfter(week.minusDays(1)))
					.filter(app -> app.getCreatedAt().toLocalDate().isBefore(week.plusDays(7)))
					.filter(app -> app.getStatus().equals(Status.DOCTOR_CANCELLED)).parallel().count();
			dto.setDoctocCancelled(totalDoctorCancelled);
			
			Long totalUserCancelled = appointment.stream().filter(app -> app.getCreatedAt().toLocalDate().isAfter(week.minusDays(1)))
					.filter(app -> app.getCreatedAt().toLocalDate().isBefore(week.plusDays(7)))
					.filter(app -> app.getStatus().equals(Status.USER_CANCELLED)).parallel().count();
			dto.setClientCancelled(totalUserCancelled);
			dto.setWeekDay(week.getDayOfMonth());
			weeklyList.add(dto);					
		});
		log.info("Getting weekly report for appointments!");
		return weeklyList;
	}
	
	private List<LocalDate> weekSlots(LocalDate start, LocalDate end){
		List<LocalDate> weekTimes = new ArrayList<>();
		long weekNumber = ChronoUnit.WEEKS.between(start, end);
		for (int i = 0; i < weekNumber; i++) {
            weekTimes.add(start);
            LocalDate endOfWeek = start.plusDays(6);
            start = endOfWeek.plusDays(1);
        }
		return weekTimes;
	}

	@Override
	public List<TotalRezervationsReportDto> getTotalReportThisYear(int year) {
		List<TotalRezervationsReportDto> dtos = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			dtos.add(getMonthlyReports(year, i));
		}
		log.info("Getting year report for appointments!");
		return dtos;
	}

	@Override
	public DentistsTotalReportsDto getDentistsMonthlyReports(int year, @NotNull @Min(1) @Max(12) int month, Status status) {
		DentistsTotalReportsDto dto = new DentistsTotalReportsDto();
		var monthname = getMonth(month,Locale.US);
		List<AppointmentEntity> appointmentList = appointmentRepo.findAllThisYear(year)
				.stream()
				.filter(app -> app.getCreatedAt().getMonth().equals(LocalDate.of(year, month, 1).getMonth()))
				.filter(app -> app.getStatus().equals(status))
				.toList();
		Map<String, Long> report = getDentistResult(appointmentList);
		
		List<WeeklyDentistsTotalReportsDto> weeklyList = getWeeklyDentistreport(appointmentList, 2021, month);
		dto.setMonthName(monthname);
		dto.setWeeklyReport(weeklyList);
		dto.setDentistsReport(report);
		
		log.info("Getting Dentist monthly report for appointments!");
		return dto;
	}
	
	private Map<String, Long> getDentistResult(List<AppointmentEntity> appointmentList){
		return appointmentList
				.stream()
				.collect(Collectors.groupingBy(AppointmentEntity::getDentist,Collectors.counting()));
	}
	
	private List<WeeklyDentistsTotalReportsDto> getWeeklyDentistreport(List<AppointmentEntity> appointments, int year, int month) {
		List<WeeklyDentistsTotalReportsDto> weeklyList = new ArrayList<>();
		List<LocalDate> weekTimes =  weekSlots(LocalDate.of(year, month, 1), LocalDate.of(year, month, 1).plusMonths(1));
		
		weekTimes.forEach(week -> {
			WeeklyDentistsTotalReportsDto dto = new WeeklyDentistsTotalReportsDto();
			List<AppointmentEntity> weekAppointments = appointments.stream().filter(app -> app.getCreatedAt().toLocalDate().isAfter(week.minusDays(1)))
					.filter(app -> app.getCreatedAt().toLocalDate().isBefore(week.plusDays(7))).toList();
			Map<String, Long>  weekReport = weekAppointments.stream().parallel()
					.collect(Collectors.groupingBy(AppointmentEntity::getDentist,Collectors.counting()));
			dto.setWeekDay(week.getDayOfMonth());
			dto.setWeeklyResult(weekReport);
			weeklyList.add(dto);
		});
		
		log.info("Getting Dentist weekly report for appointments!");
		return weeklyList;
	}

	@Override
	public List<DentistsTotalReportsDto> getDentistsTotalReports(int year) {
		List<DentistsTotalReportsDto> dtos = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			dtos.add(getDentistsMonthlyReports(year, i, Status.DONE));
		}
		log.info("Getting Dentist total report for appointments!");
		return dtos;
	}

	@Override
	public List<DentistsTotalReportsDto> getDentistsTotalCancellReports(int year) {
		List<DentistsTotalReportsDto> dtos = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			dtos.add(getDentistsMonthlyReports(year, i, Status.DOCTOR_CANCELLED));
		}
		log.info("Getting Dentist total cancelled report for appointments!");
		return dtos;
	}

	@Override
	public DentistsTotalReportsDto getDentistsMonthlyCancellReports(int year, @NotNull @Min(1) @Max(12) int month, Status status) {
		log.info("Getting Dentist monthly cancelled report for appointments!");
		return getDentistsMonthlyReports(year, month, status);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
