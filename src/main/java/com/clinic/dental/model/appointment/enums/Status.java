package com.clinic.dental.model.appointment.enums;

public enum Status {
	APPENDING("Waiting for appointment approval"),
	APPENDING_USER("Waiting user to accept new time"),
	USER_REFUZED_TIME("User refuzed time"),
	USER_CANCELLED("User Cancelled appointment"),
	DOCTOR_CANCELLED("Doctor Cancelled appointment"),
	REFUZED("Refuzed appointments"),
	ACTIVE("Active appointments"),
	DONE("Closed appointment");

	String value;

	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
