package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.clinic.dental.model.appointment.enums.AppointmentType;
import com.clinic.dental.utils.ValueOfEnum;

import lombok.Data;

@Data
public class CreatePublicAppointmentDto {

	private String dentist;
	@NotNull(message = "Please enter a valid date of format 'YYYY-MM-DD' ")
	private LocalDate date;
	@NotNull(message = "Please enter a valid start time of format 'HH:MM:SS' ")
	private LocalTime startTime;

	@NotBlank
	@ValueOfEnum(enumClass = AppointmentType.class, message = "Please enter a valid type: COMPLETE, FILLINGS, COSMETIC, IMPLANTS, ORTHODONTICS, PREVENTATIVE_CARE, PERIODONTAL_THERAPY, NUTRITIONAL_COUNSELING, ROOT_CANALS or GENERAL")
	private String type;

}
