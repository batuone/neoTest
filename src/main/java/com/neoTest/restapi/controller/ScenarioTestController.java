package com.neoTest.restapi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.neoTest.restapi.dao.ScenarioCountDAO;
import com.neoTest.restapi.model.ScenarioCountModel;
import com.neoTest.restapi.model.request.IdRequest;
import com.neoTest.restapi.model.request.ProjectIdRequest;
import com.neoTest.restapi.model.request.ScenarioIdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
	ScenarioCountDAO scenarioCountDAO;
	@Autowired
	SequenceGeneratorService seqGeneratorService;
	
	@PostMapping(value="/create")
	public ScenarioTestModel create(@RequestBody ScenarioTestModel testModel) {
		testModel.setDate(LocalDateTime.now());
		testModel.setId(seqGeneratorService.generateSequence(ScenarioTestModel.SEQUENCE_NAME));

		ScenarioCountModel scenarioCountModel = scenarioCountDAO.getByUrl(testModel.getUrl());
		testModel.setScenarioId(scenarioCountModel != null ? scenarioCountModel.getCount() :0);

		return scenarioTestDAO.save(testModel);
	}
	
	@PostMapping("/get-projectId")
	public List<ScenarioTestModel> getScenarioByProjectId(@RequestBody ProjectIdRequest request) {
		return scenarioTestDAO.getByProjectId(request.getProjectId());
	}

	@PostMapping("/get-scenarioId")
	public List<ScenarioTestModel> getByProjectIdAndScenarioId(@RequestBody ScenarioIdRequest request) {
		List<ScenarioTestModel> scenarioList = scenarioTestDAO.getByScenarioId(request.getScenarioId());
		return scenarioList.stream().filter(c -> c.getProjectId().equals(request.getProjectId()))
				.collect(Collectors.toList());
	}

	@PostMapping("/update")
	public ScenarioTestModel update(@RequestBody ScenarioTestModel testModel) {
		return scenarioTestDAO.save(testModel);
	}
	
	@PostMapping("/delete/Id")
	public void deleteById(@RequestBody IdRequest request) {
		Optional<ScenarioTestModel> scenarioObj = scenarioTestDAO.findById(request.getId());
        scenarioObj.ifPresent(c -> scenarioTestDAO.delete(c));
	}
	
}
