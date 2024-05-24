package com.neoTest.restapi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.neoTest.restapi.model.request.ScenarioTestIdRequest;
import com.neoTest.restapi.model.request.ScenarioTestProjectIdRequest;
import com.neoTest.restapi.model.request.ScenarioTestScenarioIdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoTest.restapi.dao.ScenarioTestDAO;
import com.neoTest.restapi.model.ScenarioTestModel;
import com.neoTest.restapi.service.SequenceGeneratorService;

@RestController
@RequestMapping("/api/test")
public class ScenarioTestController {

	@Autowired
	ScenarioTestDAO scenarioTestDAO;
	
	@Autowired
	SequenceGeneratorService seqGeneratorService;
	
	@PostMapping(value="/create")
	public ScenarioTestModel create(@RequestBody ScenarioTestModel testModel) {
		testModel.setDate(LocalDateTime.now());
		testModel.setId(seqGeneratorService.generateSequence(ScenarioTestModel.SEQUENCE_NAME));
		return scenarioTestDAO.save(testModel);
	}
	
	@PostMapping("/get-projectId")
	public List<ScenarioTestModel> getScenarioByProjectId(@RequestBody ScenarioTestProjectIdRequest request) {
		return scenarioTestDAO.getByProjectId(request.getProjectId());
	}

	@PostMapping("/get-scenarioId")
	public ScenarioTestModel getScenarioByScenarioId(@RequestBody ScenarioTestScenarioIdRequest request) {
		return scenarioTestDAO.getByScenarioId(request.getScenarioId());
	}

	@PostMapping("/update")
	public ScenarioTestModel update(@RequestBody ScenarioTestModel testModel) {
		return scenarioTestDAO.save(testModel);
	}
	
	@PostMapping("/delete/Id")
	public void deleteById(@RequestBody ScenarioTestIdRequest request) {
		Optional<ScenarioTestModel> scenarioObj = scenarioTestDAO.findById(request.getId());
        scenarioObj.ifPresent(c -> scenarioTestDAO.delete(c));
	}
	
}
