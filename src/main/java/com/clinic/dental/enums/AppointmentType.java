package com.clinic.dental.enums;

public enum AppointmentType {
	COMPLETE("3600"),
	FILLINGS("3600"),
	COSMETIC("3600"),
	IMPLANTS("7200"),
	ORTHODONTICS("7200"),
	PREVENTATIVE_CARE("3600"),
	PERIODONTAL_THERAPY("3600"),
	NUTRITIONAL_COUNSELING("3600"),
	ROOT_CANALS("3600"),
	GENERAL("3600");

	AppointmentType(String string) {}
}
