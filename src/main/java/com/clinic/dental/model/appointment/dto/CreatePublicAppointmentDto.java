package com.clinic.dental.model.appointment.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.clinic.dental.Utils.ValueOfEnum;
import com.clinic.dental.model.appointment.enums.AppointmentType;

import lombok.Data;

@Data
public class CreatePublicAppointmentDto {

	private String dentist;
	@NotNull(message = "Please enter a valid date of format 'YYYY-MM-DD' ")
	private LocalDate date;
	@NotNull(message = "Please enter a valid start time of format 'HH:MM:SS' ")
	private LocalTime startTime;

	@NotBlank(message = "Please enter a service type you are interested at!")
	@ValueOfEnum(enumClass = AppointmentType.class, message = "Please enter a valid type: COMPLETE,\r\n"
			+ "	FILLINGS,\r\n" + "	COSMETIC,\r\n" + "	IMPLANTS,\r\n" + "	ORTHODONTICS,\r\n"
			+ "	PREVENTATIVE_CARE,\r\n" + "	PERIODONTAL_THERAPY,\r\n" + "	NUTRITIONAL_COUNSELING,\r\n"
			+ "	ROOT_CANALS\r\n" + " or	GENERAL")
	private String type;

}
