package com.clinic.dental.exceptions;

import lombok.Data;

@Data
public class ErrorFormat {

	private String message;
	private String desc;
	private String suggestion;
}
