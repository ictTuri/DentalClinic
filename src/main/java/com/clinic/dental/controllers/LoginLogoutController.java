package com.clinic.dental.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.clinic.dental.utils.TokenUtil;

import lombok.RequiredArgsConstructor;
import lombok.var;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200",allowedHeaders = "*", allowCredentials = "true")
@RequiredArgsConstructor
public class LoginLogoutController {
	
	private static final String USER_NOT_AUTHENTICATED = "Unable to Authenticate! Check credential and password!";

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/_login")
	public ResponseEntity<TokenUtil> login(HttpServletResponse response,
			@Valid @RequestBody UsernameAndPasswordAuthenticationRequest credentials) throws AuthenticationException{
		logout();
		String token;
		try {
			var authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(credentials.getCredential(), credentials.getPassword()));

			token = jwtTokenProvider.createToken(authenticate);
			var cookie = jwtTokenProvider.createCookie(token);
			response.addCookie(cookie);
			
		}catch(AuthenticationException e) {
			throw new InvalidCredentialsException(USER_NOT_AUTHENTICATED);
		}
		TokenUtil t = new TokenUtil(token);
		return new ResponseEntity<>(t,HttpStatus.OK);
	}
	
	@PostMapping("/_logout")
	public void logout(){
		// Triggers logout
	}
}