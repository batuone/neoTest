package com.neoTest.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "scenario_test")
public class ScenarioTestModel {
	
	public static final String SEQUENCE_NAME = "scenario_test_sequence";

	@Id
	private Long id;
	private String user;
	private String eventType;
	private LocalDateTime date;
	private String elementType;
	private String elementId;
	private String elementValue;
	private String elementClass;
	private String url;
	private String pathX;
	private int coordinateX;
	private int coordinateY;
	private String sessionId;
	private String projectId;
	private int scenarioId;
	private String scenarioText;
}
