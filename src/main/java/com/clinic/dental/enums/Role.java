package com.clinic.dental.enums;

public enum Role {
	DOCTOR("Access his schedule and manage his/her appointment"),
	SECRETARY("Access all apointments and manage them"),
	PUBLIC("Check calendar and setup appoinments on free times");

	Role(String string) {}
}
