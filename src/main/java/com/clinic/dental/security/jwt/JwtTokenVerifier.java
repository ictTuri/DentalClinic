package com.clinic.dental.security.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.var;

@RequiredArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter{

	private final SecretKey secretKey;
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return new AntPathMatcher().matchStart("swagger", request.getRequestURL().toString());
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var cookie = WebUtils.getCookie(request, "jwttoken");
		if (cookie == null) {
			response.sendError(404, "Not authenticated!");
			return;
		}

		String token = cookie.getValue();
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			var body = claimsJws.getBody();
			String username = body.getSubject();
			@SuppressWarnings("unchecked")
			var authorities = (List<Map<String, String>>) body.get("authorities");

			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream().map(auth -> new SimpleGrantedAuthority(auth.get("authority").toString())).collect(Collectors.toSet());

			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);

			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (JwtException e) {
			SecurityContextHolder.clearContext();
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
		}
		filterChain.doFilter(request, response);
	}
}
