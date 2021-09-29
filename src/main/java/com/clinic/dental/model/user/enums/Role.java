package com.clinic.dental.model.user.enums;

public enum Role {
	ADMIN("GOD Mode"),
	DOCTOR("Access his schedule and manage his/her appointment"),
	SECRETARY("Access all apointments and manage them"),
	PUBLIC("Check calendar and setup appoinments on free times");

	String value;

	private Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
