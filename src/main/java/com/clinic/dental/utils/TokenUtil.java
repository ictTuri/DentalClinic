package com.clinic.dental.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenUtil {
	private String token;
	
	public TokenUtil(String token){
		this.token = token;
	}
	
}
