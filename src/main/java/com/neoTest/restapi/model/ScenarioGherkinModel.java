package com.neoTest.restapi.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "scenario_gherkin")
public class ScenarioGherkinModel {

	public static final String SEQUENCE_NAME = "scenario_gherkin_sequence";

	@Id
	private Long id;
	private String url;
	private String projectId;
	private int scenarioId;
	private String gherkin;

}
