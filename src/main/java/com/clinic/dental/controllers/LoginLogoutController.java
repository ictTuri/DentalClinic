package com.clinic.dental.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinic.dental.exceptions.InvalidCredentialsException;
import com.clinic.dental.security.dto.UsernameAndPasswordAuthenticationRequest;
import com.clinic.dental.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.var;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true", origins = {"https://dental-clinic7.web.app","http://localhost:4200"})
@RequiredArgsConstructor
public class LoginLogoutController {
	
	private static final String USER_NOT_AUTHENTICATED = "Unable to Authenticate! Check credential and password!";

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/_login")
	public Map<String, String> login(HttpServletResponse response,
			@Valid @RequestBody UsernameAndPasswordAuthenticationRequest credentials) throws AuthenticationException{
		logout();
		String token;
		try {
			var authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(credentials.getCredential(), credentials.getPassword()));

			token = jwtTokenProvider.createToken(authenticate);
			
//			var cookie = jwtTokenProvider.createCookie(token);
//			response.addCookie(cookie);
			
			 response.setHeader("Set-Cookie", "jwttoken="+token+" ; Secure; HttpOnly; SameSite=None");
			
		}catch(AuthenticationException e) {
			throw new InvalidCredentialsException(USER_NOT_AUTHENTICATED);
		}
		Map<String, String> result = new HashMap<>();
		result.put("token", token);
		return result;
	}
	
	@PostMapping("/_logout")
	public void logout(){
		// Triggers logout
	}
}