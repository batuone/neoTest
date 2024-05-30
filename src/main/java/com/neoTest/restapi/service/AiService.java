package com.neoTest.restapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neoTest.restapi.dao.ScenarioGherkinDAO;
import com.neoTest.restapi.dao.SuggestionDAO;
import com.neoTest.restapi.model.*;
import com.neoTest.restapi.model.request.AiContentRequest;
import com.neoTest.restapi.model.response.CompareAiResultResponse;
import com.neoTest.restapi.model.response.GherkinResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AiService {
	
	@Autowired 
	private ScenarioGherkinDAO scenarioGherkinDAO;
	@Autowired
	private RestCallerService restCallerService;
	@Value("${ai.createTestScenarios}")
	private String createTestScenariosUrl;
	@Value("${ai.createGherkin}")
	private String createGherkinUrl;
	@Value("${ai.compareTestScenarios}")
	private String compareTestScenariosUrl;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	SuggestionDAO suggestionDAO;
	@Autowired
	SequenceGeneratorService seqGeneratorService;

	public void scenarioGherkinSave(ScenarioTestModel testModel, String responseBody) {
		ScenarioGherkinModel gModel = new ScenarioGherkinModel();
		gModel.setId(seqGeneratorService.generateSequence(ScenarioGherkinModel.SEQUENCE_NAME));
		gModel.setUrl(testModel.getUrl());
		gModel.setScenarioId(testModel.getScenarioId());
		gModel.setProjectId(testModel.getProjectId());
		gModel.setGherkin(responseBody);
		scenarioGherkinDAO.save(gModel);
	}

	public void createTestScenarios (List<ScenarioTestModel> testModelList) throws JsonProcessingException {
		if (CollectionUtils.isEmpty(testModelList)) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		testModelList.forEach(c-> {
			sb.append(c.getScenarioText());
			sb.append(System.lineSeparator());
		});
		AiContentRequest request = new AiContentRequest();
		request.setId(String.valueOf(testModelList.get(0).getScenarioId()));
		request.setContent(sb.toString());
		String jsonString = objectMapper.writeValueAsString(request);
		String responseBody = restCallerService.callRest(createTestScenariosUrl, jsonString);

		scenarioGherkinSave(testModelList.get(0), responseBody);
	}

	public void createProdScenarios(List<ScenarioProdModel> prodModelList) throws JsonProcessingException {
		if (CollectionUtils.isEmpty(prodModelList)) {
			return;
		}
		StringBuilder sb = new StringBuilder();
		prodModelList.forEach(c-> {
			sb.append(c.getScenarioText());
			sb.append(System.lineSeparator());
		});

		//gherkin convert
		AiContentRequest request = new AiContentRequest();
		request.setContent(sb.toString());
		String jsonString = objectMapper.writeValueAsString(request);
		String responseBody = restCallerService.callRest(createGherkinUrl, jsonString);
		GherkinResultResponse gherkinResult = objectMapper.readValue(responseBody, GherkinResultResponse.class);

		//olusan gherkin ile compare senaryo
		AiContentRequest compareAiRequest = new AiContentRequest();
		compareAiRequest.setId(String.valueOf(prodModelList.get(0).getScenarioId()));
		compareAiRequest.setContent(gherkinResult.getResult());
		String compareAiJsonString = objectMapper.writeValueAsString(compareAiRequest);
		String response = restCallerService.callRest(compareTestScenariosUrl, compareAiJsonString);
		CompareAiResultResponse compareResponse = objectMapper.readValue(response, CompareAiResultResponse.class);

		//compare sonrasi sonucu suggestion tablosuna yazma
		SuggestionModel suggestionModel = new SuggestionModel();
		suggestionModel.setDate(LocalDateTime.now());
		suggestionModel.setId(seqGeneratorService.generateSequence(SuggestionModel.SEQUENCE_NAME));
		suggestionModel.setProjectId(prodModelList.get(0).getProjectId());
		suggestionModel.setScenarioText(compareResponse.getContent());
		suggestionModel.setIsAccepted(false);
		suggestionModel.setScenarioId(prodModelList.get(0).getScenarioId());
		suggestionModel.setAnomaly(compareResponse.getAnomaly());
		suggestionDAO.save(suggestionModel);
	}

}
