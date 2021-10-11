package com.clinic.dental.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.clinic.dental.model.appointment.AppointmentEntity;
import com.clinic.dental.model.appointment.dto.SlotDto;
import com.clinic.dental.model.appointment.enums.AppointmentType;
import com.clinic.dental.model.appointment.enums.Status;

public class AppointmentUtilTest {
	public static AppointmentEntity appointmentUserRefuzed () {
		AppointmentEntity app = new AppointmentEntity();
		app.setDate(LocalDate.of(2021, 11, 2));
		app.setDentist("dentistname");
		app.setId(1L);
		app.setPatient(UserUtilTest.publicOne());
		app.setStartTime(LocalTime.of(14, 00));
		app.setEndTime(LocalTime.of(15, 00));
		app.setStatus(Status.USER_REFUZED_TIME);
		app.setType(AppointmentType.COSMETIC);
		app.setCreatedAt(LocalDateTime.now());
		return app;
	}
	
	public static AppointmentEntity appointmentUserApprove () {
		AppointmentEntity app = new AppointmentEntity();
		app.setDate(LocalDate.of(2021, 11, 2));
		app.setDentist("dentistname");
		app.setId(1L);
		app.setPatient(UserUtilTest.publicOne());
		app.setStartTime(LocalTime.of(14, 00));
		app.setEndTime(LocalTime.of(15, 00));
		app.setStatus(Status.ACTIVE);
		app.setType(AppointmentType.COSMETIC);
		app.setCreatedAt(LocalDateTime.now());
		return app;
	}
	
	public static AppointmentEntity appointmentClosed() {
		AppointmentEntity app = new AppointmentEntity();
		app.setDate(LocalDate.of(2021, 11, 2));
		app.setDentist("dentistname");
		app.setId(1L);
		app.setPatient(UserUtilTest.publicOne());
		app.setStartTime(LocalTime.of(14, 00));
		app.setEndTime(LocalTime.of(15, 00));
		app.setStatus(Status.DONE);
		app.setType(AppointmentType.COSMETIC);
		app.setCreatedAt(LocalDateTime.now());
		return app;
	}
	
	public static AppointmentEntity appointmentFour () {
		AppointmentEntity app = new AppointmentEntity();
		app.setDate(LocalDate.of(2021, 11, 5));
		app.setDentist("dentistname");
		app.setId(1L);
		app.setPatient(UserUtilTest.publicOne());
		app.setStartTime(LocalTime.of(15, 00));
		app.setEndTime(LocalTime.of(16, 00));
		app.setStatus(Status.USER_CANCELLED);
		app.setType(AppointmentType.COSMETIC);
		app.setCreatedAt(LocalDateTime.now());
		return app;
	}
	

	public static AppointmentEntity rezervedAppointment() {
		AppointmentEntity app = new AppointmentEntity();
		app.setDate(LocalDate.of(2021, 11, 2));
		app.setDentist("dentistname");
		app.setId(1L);
		app.setPatient(UserUtilTest.publicOne());
		app.setStartTime(LocalTime.of(15, 00));
		app.setEndTime(LocalTime.of(16, 00));
		app.setStatus(Status.APPENDING);
		app.setType(AppointmentType.IMPLANTS);
		app.setCreatedAt(LocalDateTime.now());
		return app;
	}
	
	public static AppointmentEntity appointmentSix() {
		AppointmentEntity app = new AppointmentEntity();
		app.setDate(LocalDate.of(2021, 11, 12));
		app.setDentist("newDentist");
		app.setId(1L);
		app.setPatient(UserUtilTest.publicOne());
		app.setStartTime(LocalTime.of(15, 00));
		app.setEndTime(LocalTime.of(16, 00));
		app.setStatus(Status.ACTIVE);
		app.setType(AppointmentType.IMPLANTS);
		app.setCreatedAt(LocalDateTime.now());
		return app;
	}
	
	public static AppointmentEntity appointmentFive() {
		AppointmentEntity app = new AppointmentEntity();
		app.setDate(LocalDate.of(2021, 11, 12));
		app.setDentist("doctorOne");
		app.setId(1L);
		app.setPatient(UserUtilTest.publicOne());
		app.setStartTime(LocalTime.of(15, 00));
		app.setEndTime(LocalTime.of(16, 00));
		app.setStatus(Status.APPENDING_USER);
		app.setType(AppointmentType.IMPLANTS);
		app.setCreatedAt(LocalDateTime.now());
		return app;
	}

	
	  
			
	
	public static List<SlotDto> getSLots(){ 
		List<LocalDateTime> timeList = getSevenDaysForward(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS),LocalDateTime.now().plusWeeks(1L));
		String[] doctors = AppointmentDtoUtilTest.getDoctors();
		List<SlotDto> getSLots =  new ArrayList<>();
		timeList.stream()
		.map(time -> getSLots.add(
				new SlotDto(time.toLocalDate(), time.toLocalTime(), time.plusHours(1).toLocalTime(), doctors)))
		.collect(Collectors.toList());
		return getSLots;
	}
		
	public static List<LocalDateTime> getSevenDaysForward(LocalDateTime startDate, LocalDateTime endDate) {
		long numOfDaysBetween = ChronoUnit.HOURS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusHours(i))
				.filter(i -> isWorkingHours(i)).filter(i -> isWorkingDay(i)).collect(Collectors.toList());
	}

	private static boolean isWorkingDay(final LocalDateTime time) {
		return time.getDayOfWeek().getValue() < DayOfWeek.SATURDAY.getValue();
	}

	private static boolean isWorkingHours(final LocalDateTime time) {
		int hour = time.getHour();
		return 8 <= hour && hour <= 16;
	}

}
