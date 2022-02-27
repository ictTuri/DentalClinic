package com.clinic.dental.security.jwt;

import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


//import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.var;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;

	public String createToken(Authentication authentication) {

		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim("authorities", authentication.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
//				.compressWith(CompressionCodecs.DEFLATE)
				.signWith(secretKey).compact();

	}

	public Cookie createCookie(String token) {
		var cookie = new Cookie("jwttoken", token);
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setHttpOnly(true);
		cookie.setMaxAge(60*60*24);
		return cookie;
	}
}
