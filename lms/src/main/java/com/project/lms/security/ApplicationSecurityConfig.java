package com.project.lms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


import static com.project.lms.enums.Roles.*;
import static com.project.lms.enums.Permissions.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter{

	private final PasswordEncoder passwordEncoder;
	
	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
		super();
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
//		.antMatchers(HttpMethod.DELETE,"/api/v1/books/**", "/api/v1/books/").hasAuthority(BOOK_WRITE.getPermission())
//		.antMatchers(HttpMethod.POST,"/api/v1/books/**", "/api/v1/books/").hasAuthority(BOOK_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT,"/api/v1/books/**", "/api/v1/books/").hasAuthority(BOOK_WRITE.getPermission())
//		.antMatchers(HttpMethod.GET,"/api/v1/books/**", "/api/v1/books/").hasAnyRole(SECRETARY.name(), STUDENT.name())
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails student = User.builder()
				.username("student")
				.password(passwordEncoder.encode("student"))
//				.roles(STUDENT.name())
				.authorities(STUDENT.getGrantedAuthorities())
				.build();
		
		UserDetails secretary = User.builder()
				.username("secretary")
				.password(passwordEncoder.encode("secretary"))
//				.roles(SECRETARY.name())
				.authorities(SECRETARY.getGrantedAuthorities())
				.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder.encode("admin"))
//				.roles(ADMIN.name())
				.authorities(ADMIN.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(student,secretary,admin);
	}

	
	private String[] getUrlDisabledPaths() {
		return new String[] { "/api/login", "/open/**", "/api/register/**","/api/v1/properties","/api/v1/download","/api/v1/properties/{\\d+}", "/webjars/**", "/v2/api-docs/**",
				"/swagger-resources/**", "/swagger-ui.html", "/swagger/**", "/favicon.ico", "/api/swagger.json",
				"/actuator/health" };
	}
	
}
