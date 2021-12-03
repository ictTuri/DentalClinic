package com.clinic.dental.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.clinic.dental.security.dto.UsernameAndPasswordAuthenticationRequest;
import com.clinic.dental.security.jwt.JwtTokenProvider;
import com.clinic.dental.utils.TokenUtil;


@ExtendWith(MockitoExtension.class)
class LoginLogoutControllerTest {
	@InjectMocks
	LoginLogoutController inoutController;
	@Mock
	AuthenticationManager authenticationManager;
	@Mock
	JwtTokenProvider jwtTokenProvider;
	
	@BeforeEach
	void setup() {
		inoutController = new LoginLogoutController(authenticationManager,jwtTokenProvider);
	}
	
	@Test
	void givenValidCredentials_WhenLogin_ThenSuccessful() {
		UsernameAndPasswordAuthenticationRequest cred = new UsernameAndPasswordAuthenticationRequest();
		cred.setCredential("0693636366");
		cred.setPassword("1234");
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(cred.getCredential(),cred.getPassword());
		when(authenticationManager.authenticate(auth)).thenReturn(auth);
		when(jwtTokenProvider.createToken(auth)).thenReturn("toooooken");
		when(jwtTokenProvider.createCookie("toooooken")).thenReturn(new Cookie("cookie","token"));
		
		HttpServletResponse httpServletResponse = new MockHttpServletResponse();

		ResponseEntity<TokenUtil> response = inoutController.login(httpServletResponse, cred);
//
//		assertEquals("Login successfully ", response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response);
	}

	@Test
	void LogoutVoidTest() {
		inoutController.logout();
	}

}
