package com.project.lms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

import java.util.Arrays;
import java.util.stream.Collectors;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Resource(name = "userDetailsService")
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtAuthenticationProvider jwtAuthenticationProvider;

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
		return new JwtAuthenticationFilter(authenticationManagerBean(), authenticationEntryPoint);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.authenticationProvider(jwtAuthenticationProvider);
	}

	@Override
	public void configure(WebSecurity http) {
		http.ignoring().antMatchers(getUrlDisabledPaths());
		http.ignoring().antMatchers(HttpMethod.OPTIONS);
	}

	@Bean
	public CorsFilter corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.setMaxAge((long) 3600);
		configuration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
		configuration.setAllowedMethods(
				Arrays.asList(HttpMethod.values()).stream().map(HttpMethod::name).collect(Collectors.toList()));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return new CorsFilter(source);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
	        http.csrf().disable().authorizeRequests()
	        
	        		.antMatchers("/api/**").permitAll().and().sessionManagement()
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	                .addFilterAfter(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

	        // @formatter:on
	}

	private String[] getUrlDisabledPaths() {
		return new String[] { "/api/login","/api/v1/register", "/open/**", "/api/register/**","/api/v1/properties","/api/v1/download","/api/v1/properties/{\\d+}", "/webjars/**", "/v2/api-docs/**",
				"/swagger-resources/**", "/swagger-ui.html", "/swagger/**", "/favicon.ico", "/api/swagger.json",
				"/actuator/health" };
	}

}
