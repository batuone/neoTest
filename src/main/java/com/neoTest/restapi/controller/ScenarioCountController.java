package com.neoTest.restapi.controller;

import com.neoTest.restapi.dao.ScenarioCountDAO;
import com.neoTest.restapi.model.ScenarioCountModel;
import com.neoTest.restapi.model.request.ScenarioUrlRequest;
import com.neoTest.restapi.model.response.CheckScenarioOpenResponse;
import com.neoTest.restapi.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/count")
public class ScenarioCountController {

	@Autowired
	ScenarioCountDAO scenarioCountDAO;
	
	@Autowired
	SequenceGeneratorService seqGeneratorService;

	@PostMapping(value="/create")
	public ScenarioCountModel create(@RequestBody ScenarioCountModel model) {
		model.setId(seqGeneratorService.generateSequence(ScenarioCountModel.SEQUENCE_NAME));
		model.setCount(0);
		model.setIsOpen(Boolean.FALSE);
		return scenarioCountDAO.save(model);
	}
	
	@PostMapping("/check/scenario-open")
	public CheckScenarioOpenResponse checkScenarioOpen(@RequestBody ScenarioUrlRequest request) {
		CheckScenarioOpenResponse openResponse = new CheckScenarioOpenResponse();
		ScenarioCountModel response = scenarioCountDAO.getByUrl(request.getUrl());
		openResponse.setIsOpen(response.getIsOpen());
		return openResponse;
	}

	@PostMapping("/start/scenario")
	public ScenarioCountModel startScenario(@RequestBody ScenarioUrlRequest request) {
		ScenarioCountModel response = scenarioCountDAO.getByUrl(request.getUrl());
		response.setIsOpen(Boolean.TRUE);
		response.setCount(response.getCount() + 1);
		return scenarioCountDAO.save(response);
	}

	@PostMapping("/finish/scenario")
	public ScenarioCountModel finishScenario(@RequestBody ScenarioUrlRequest request) {
		ScenarioCountModel response = scenarioCountDAO.getByUrl(request.getUrl());
		response.setIsOpen(Boolean.FALSE);
		return scenarioCountDAO.save(response);
	}





}
