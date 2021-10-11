package com.clinic.dental.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.clinic.dental.model.appointment.dto.ChangeAppointmentDentistDto;
import com.clinic.dental.model.appointment.dto.ChangeAppointmentTimeDto;
import com.clinic.dental.model.appointment.dto.CreatePublicAppointmentDto;
import com.clinic.dental.model.appointment.enums.AppointmentType;

public class AppointmentDtoUtilTest {
	public static ChangeAppointmentTimeDto changeTimeOne() {
		ChangeAppointmentTimeDto dto = new ChangeAppointmentTimeDto();
		dto.setDay(LocalDate.of(2021, 11, 12));
		dto.setStartTime(LocalTime.of(15, 00));
		return dto;
	}
	
	public static ChangeAppointmentDentistDto changeDentistOne() {
		ChangeAppointmentDentistDto dto = new ChangeAppointmentDentistDto();
		dto.setDentist("newDentist");
		return dto;
	}
	
	public static CreatePublicAppointmentDto rezerveSlot() {
		CreatePublicAppointmentDto dto = new CreatePublicAppointmentDto();
		dto.setDate(LocalDate.of(2021, 11, 2));
		dto.setDentist("dentistname");
		dto.setStartTime(LocalTime.of(15, 00));
		dto.setType(AppointmentType.GENERAL.name());
		return dto;
	}
	
	public static String[] getDoctors(){
		List<String> list = new ArrayList<>();
		list.add("doctorname");
		String[] returnList = list.toArray(new String[0]);
		return returnList;
	}
}
