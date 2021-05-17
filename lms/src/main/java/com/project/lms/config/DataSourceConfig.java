package com.project.lms.config;

import javax.activation.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@Profile({"sql","mongo"})
public class DataSourceConfig {
	@Bean
	 @Profile("sql")
	 public DataSource devDataSource() {
		return null;
	 }

	@Bean
	 @Profile("mongo")
	 public DataSource prodDataSource() {
		return null;
	 
	 }
}
