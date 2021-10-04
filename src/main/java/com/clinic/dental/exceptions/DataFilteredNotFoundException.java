package com.clinic.dental.exceptions;

public class DataFilteredNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public DataFilteredNotFoundException(String message) {
		super(message);
	}
}
