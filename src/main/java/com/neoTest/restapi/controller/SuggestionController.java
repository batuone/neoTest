package com.neoTest.restapi.controller;

import com.neoTest.restapi.dao.SuggestionDAO;
import com.neoTest.restapi.model.ScenarioTestModel;
import com.neoTest.restapi.model.SuggestionModel;
import com.neoTest.restapi.model.request.IdRequest;
import com.neoTest.restapi.model.request.ProjectIdRequest;
import com.neoTest.restapi.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/suggestion")
public class SuggestionController {

	@Autowired
	SuggestionDAO suggestionDAO;
	
	@Autowired
	SequenceGeneratorService seqGeneratorService;
	
	@PostMapping(value="/create")
	public SuggestionModel create(@RequestBody SuggestionModel suggestionModel) {
		suggestionModel.setDate(LocalDateTime.now());
		suggestionModel.setId(seqGeneratorService.generateSequence(SuggestionModel.SEQUENCE_NAME));
		return suggestionDAO.save(suggestionModel);
	}
	
	@PostMapping("/get-projectId")
	public List<SuggestionModel> getSuggestionByProjectId(@RequestBody ProjectIdRequest request) {
		return suggestionDAO.getByProjectId(request.getProjectId());
	}

	@PostMapping("/update")
	public SuggestionModel update(@RequestBody SuggestionModel suggestionModel) {
		return suggestionDAO.save(suggestionModel);
	}

	@PostMapping("/update")
	public void update(@RequestBody IdRequest idRequest) {
		Optional<SuggestionModel> model = suggestionDAO.findById(idRequest.getId());
		if(!model.isPresent())
		{
			return;
		}
		model.get().setIsAccepted(Boolean.TRUE);
		suggestionDAO.save(model.get());



	}

}
