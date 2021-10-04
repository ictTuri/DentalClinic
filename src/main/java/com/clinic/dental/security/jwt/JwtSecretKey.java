package com.clinic.dental.security.jwt;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtSecretKey {

	private final JwtConfig jwtConfig;
	
	@Bean
	public SecretKey secretKey() {
		final String encodedKey = Encoders.BASE64.encode(jwtConfig.getSecretKey().getBytes());
		return Keys.hmacShaKeyFor(encodedKey.getBytes());
	}
}
