package com.project.lms.jwt;

import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;

@Component
public class TokenProvider{
	
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	
	 public TokenProvider(JwtConfig jwtConfig, SecretKey secretKey) {
		super();
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
	}


	public String createToken(Authentication authentication) {
			return  Jwts.builder().setSubject(authentication.getName())
					.claim("authorities", authentication.getAuthorities())
					.setIssuedAt(new Date())
					.setExpiration(java.sql.Date.valueOf(LocalDate.now()
							.plusDays(jwtConfig.getTokenExpirationAfterDays())))
					.compressWith(CompressionCodecs.DEFLATE)
					.signWith(secretKey).compact();

	   }
	
	public Cookie createCookie(String token) {
		var cookie = new Cookie("token", token);
		cookie.setPath("/");
	    cookie.setSecure(true);
	    cookie.setHttpOnly(true);
	    return cookie;
   }
}
