package com.clinic.dental.model.appointment.enums;

public enum Status {
	APPENDING("Waiting for appointment approval"),
	REFUZED("Refuzed appointments"),
	ACTIVE("Active appointments"),
	CLOSED("Closed appointment");

	String value;

	private Status(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
