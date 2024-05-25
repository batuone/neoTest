package com.neoTest.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "scenario_suggestion")
public class SuggestionModel {
	
	public static final String SEQUENCE_NAME = "scenario_suggestion_sequence";

	@Id
	private Long id;
	private LocalDateTime date;
	private String projectId;
	private String scenarioText;
	private Boolean isAccepted;

}
