package com.project.lms.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

public class JwtTokenVerifier extends OncePerRequestFilter {
	private final SecretKey secretKey;

	public JwtTokenVerifier(SecretKey secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Cookie cookie = WebUtils.getCookie(request, "token");
		if(cookie == null) {
			 throw new SecurityException("JWT token missing");
		}
			
		String token = cookie.getValue();
		try {	
			Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
			Claims body = claimsJws.getBody();
			String username = body.getSubject();
			@SuppressWarnings("unchecked")
			var authorities = (List<Map<String, String>>) body.get("authorities");

			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
					.map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toSet());

			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null,
					simpleGrantedAuthorities);

			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (JwtException e) {
			SecurityContextHolder.clearContext();
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
		}
		filterChain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return new AntPathMatcher().matchStart("swagger",  request.getServletPath());
	}

}
