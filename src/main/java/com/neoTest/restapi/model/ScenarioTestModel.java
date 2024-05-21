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
	private String scenario;
	private LocalDateTime date;
	private int xCoordinate;
	private int deneme;
}
