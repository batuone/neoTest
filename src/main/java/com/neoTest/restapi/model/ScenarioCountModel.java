package com.neoTest.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "scenario_count")
public class ScenarioCountModel {
	
	public static final String SEQUENCE_NAME = "scenario_count_sequence";

	@Id
	private Long id;
	private String url;
	private String projectId;
	private Boolean isOpen;
	private int count;
}
