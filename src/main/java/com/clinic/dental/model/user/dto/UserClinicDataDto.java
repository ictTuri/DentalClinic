package com.clinic.dental.model.user.dto;

import java.util.List;

import com.clinic.dental.model.appointment.dto.DisplayAppointmentDto;

import lombok.Data;

@Data
public class UserClinicDataDto {

	private String firstName;
	private String lastName;
	private String email;
	private String NID;
	private String phone;
	private int age;
	private List<DisplayAppointmentDto> appointments;
}
