package com.project.lms.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "application.jwt")
@Component
public class JwtConfig {
	 private String secretKey;
	    private String tokenPrefix;
	    private Integer tokenExpirationAfterDays;
	    
	    public String getAuthorizationHeader() {
	        return HttpHeaders.AUTHORIZATION;
	    }
}
