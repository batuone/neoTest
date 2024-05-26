package com.neoTest.restapi.controller;

import com.neoTest.restapi.dao.ScenarioCountDAO;
import com.neoTest.restapi.dao.ScenarioProdDAO;
import com.neoTest.restapi.model.ScenarioCountModel;
import com.neoTest.restapi.model.ScenarioProdModel;
import com.neoTest.restapi.model.request.IdRequest;
import com.neoTest.restapi.model.request.ProjectIdRequest;
import com.neoTest.restapi.model.request.ScenarioIdRequest;
import com.neoTest.restapi.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prod")
public class ScenarioProdController {

	@Autowired
	ScenarioProdDAO scenarioProdDAO;
	@Autowired
	ScenarioCountDAO scenarioCountDAO;
	@Autowired
	SequenceGeneratorService seqGeneratorService;

	@PostMapping(value="/create")
	public ScenarioProdModel create(@RequestBody ScenarioProdModel prodModel) {
		prodModel.setDate(LocalDateTime.now());
		prodModel.setId(seqGeneratorService.generateSequence(ScenarioProdModel.SEQUENCE_NAME));

		ScenarioCountModel scenarioCountModel = scenarioCountDAO.getByUrl(prodModel.getUrl());
		prodModel.setScenarioId(scenarioCountModel.getCount());

		return scenarioProdDAO.save(prodModel);
	}

	@PostMapping("/get-projectId")
	public List<ScenarioProdModel> getScenarioByProjectId(@RequestBody ProjectIdRequest request) {
		return scenarioProdDAO.getByProjectId(request.getProjectId());
	}

	@PostMapping("/get-scenarioId")
	public ScenarioProdModel getScenarioByScenarioId(@RequestBody ScenarioIdRequest request) {
		return scenarioProdDAO.getByScenarioId(request.getScenarioId());
	}

	@PostMapping("/update")
	public ScenarioProdModel update(@RequestBody ScenarioProdModel prodModel) {
		return scenarioProdDAO.save(prodModel);
	}

	@PostMapping("/delete/Id")
	public void deleteById(@RequestBody IdRequest request) {
		Optional<ScenarioProdModel> scenarioObj = scenarioProdDAO.findById(request.getId());
		scenarioObj.ifPresent(c -> scenarioProdDAO.delete(c));
	}
	
}
