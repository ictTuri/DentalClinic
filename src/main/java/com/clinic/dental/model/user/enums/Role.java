package com.clinic.dental.model.user.enums;

public enum Role {
	ROLE_ADMIN("GOD Mode"),
	ROLE_DOCTOR("Access his schedule and manage his/her appointment"),
	ROLE_SECRETARY("Access all apointments and manage them"),
	ROLE_PUBLIC("Check calendar and setup appoinments on free times");

	String value;

	private Role(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
