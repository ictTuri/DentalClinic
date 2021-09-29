package com.clinic.dental.enums;

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
