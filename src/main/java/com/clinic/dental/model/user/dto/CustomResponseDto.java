package com.clinic.dental.model.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomResponseDto {

	private String message;
	private LocalDateTime time;
}
