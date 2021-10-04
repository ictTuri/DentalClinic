package com.clinic.dental.exceptions;

public class DataIdNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public DataIdNotFoundException(String message) {
		super(message);
	}
}
