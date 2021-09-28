package com.clinic.dental.enums;

public enum Status {
	APPENDING("Waiting for appointment approval"),
	REFUZED("Refuzed appointments"),
	ACTIVE("Active appointments"),
	CLOSED("Closed appointment");

	Status(String string) {}
}
