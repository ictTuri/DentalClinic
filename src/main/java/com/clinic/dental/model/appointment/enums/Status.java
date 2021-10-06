package com.clinic.dental.model.appointment.enums;

public enum Status {
	APPENDING("Waiting for appointment approval"),
	APPENGING_USER("Waiting user to accept new time"),
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
