package com.clinic.dental.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
@RequiredArgsConstructor
public class LoginLogoutController {
	
	private static final String USERNOTAUTHENTICATED = "Unable to Authenticate! Check username and password!";

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/_login")
	public ResponseEntity<String> login(HttpServletResponse response,
			@Valid @RequestBody UsernameAndPasswordAuthenticationRequest credentials) throws AuthenticationException{
		logout();
		try {
			var authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(credentials.getCredential(), credentials.getPassword()));

			String token = jwtTokenProvider.createToken(authenticate);
			var cookie = jwtTokenProvider.createCookie(token);
			response.addCookie(cookie);
			
		}catch(AuthenticationException e) {
			throw new InvalidCredentialsException(USERNOTAUTHENTICATED);
		}
		return new ResponseEntity<>("Login successfully ",HttpStatus.OK);
	}
	
	@PostMapping("/_logout")
	public void logout(){
		// Triggers logout
	}
}