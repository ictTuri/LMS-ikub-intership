package com.project.lms.mongoconfig;

import javax.persistence.Id;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Profile("mongo")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "database_sequence")
public class DatabaseSequence {

	@Id
	private String id;
	
	private Long seq;
}
