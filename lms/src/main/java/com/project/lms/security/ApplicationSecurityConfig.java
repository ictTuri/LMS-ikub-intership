package com.project.lms.security;

import javax.crypto.SecretKey;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.project.lms.auth.ApplicationUserService;
import com.project.lms.auth.DeniedAccessHandlerCustom;
import com.project.lms.auth.RestAuthenticationEntryPoint;
import com.project.lms.jwt.JwtTokenVerifier;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;
	private final LogoutSuccessHandler logoutSuccess;
	private final SecretKey secretKey;
	private final RestAuthenticationEntryPoint authenticationEntryPoint;
	private final DeniedAccessHandlerCustom accessDeniedHandler;

	public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService,
			LogoutSuccessHandler logoutSuccess, SecretKey secretKey,
			RestAuthenticationEntryPoint authenticationEntryPoint,
			DeniedAccessHandlerCustom accessDeniedHandler) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
		this.logoutSuccess = logoutSuccess;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.secretKey = secretKey;
		this.accessDeniedHandler = accessDeniedHandler;
	}
	
	public JwtTokenVerifier jwtTokenVerifier(){
        return new JwtTokenVerifier(secretKey);
    }

	@Override
    public void configure(WebSecurity http) {
        http.ignoring().antMatchers(getDisabledUrlPaths());
        http.ignoring().antMatchers(HttpMethod.OPTIONS);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		http.csrf().disable()
						.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
						.and()
						.addFilterAfter(jwtTokenVerifier(), UsernamePasswordAuthenticationFilter.class)
						.authorizeRequests()
						.anyRequest()
						.authenticated()
						.and()
						.logout()
						.logoutUrl("/api/v1/_logout").logoutSuccessHandler(logoutSuccess)
						.deleteCookies("token").invalidateHttpSession(true);
		
		http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		var provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

	private String[] getDisabledUrlPaths() {
		return new String[] {"/api/v1/register", "/api/v1/_login", "/open/**", "/h2-console/**","/h2-console", "/api/register", "/webjars/**", "/v2/api-docs/**",
				"/swagger-resources/**", "/swagger-ui.html", "/swagger/**", "/favicon.ico", "/api/swagger.json",
				"/actuator/health" };
	}
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

}
