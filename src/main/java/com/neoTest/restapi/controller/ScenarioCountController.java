package com.neoTest.restapi.controller;

import com.neoTest.restapi.dao.ScenarioCountDAO;
import com.neoTest.restapi.dao.ScenarioProdDAO;
import com.neoTest.restapi.dao.ScenarioTestDAO;
import com.neoTest.restapi.model.ScenarioCountModel;
import com.neoTest.restapi.model.ScenarioTestModel;
import com.neoTest.restapi.model.ScenarioProdModel;
import com.neoTest.restapi.model.request.ScenarioUrlRequest;
import com.neoTest.restapi.model.response.CheckScenarioOpenResponse;
import com.neoTest.restapi.service.RestCallerService;
import com.neoTest.restapi.service.SequenceGeneratorService;
import com.neoTest.restapi.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/count")
public class ScenarioCountController {

    @Autowired
    ScenarioCountDAO scenarioCountDAO;
    @Autowired
    ScenarioTestDAO scenarioTestDAO;
    @Autowired
    ScenarioProdDAO scenarioProdDAO;
    @Autowired
    SequenceGeneratorService seqGeneratorService;
    @Autowired
    AiService aiService;
    @Autowired
    RestCallerService restCallerService;

    @PostMapping(value = "/create")
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
        try {
            ScenarioCountModel response = scenarioCountDAO.getByUrl(request.getUrl());
            response.setIsOpen(Boolean.FALSE);
            ScenarioCountModel saveModel = scenarioCountDAO.save(response);

            if (response.getEnv().equals("test")) {
                List<ScenarioTestModel> testModelList =
                        scenarioTestDAO.getByScenarioIdAndProjectIdAndUrl(response.getCount(),
                                response.getProjectId(), response.getUrl());
                aiService.createTestScenarios(testModelList);
            } else if (response.getEnv().equals("prod")) {
                List<ScenarioProdModel> prodModelList =
                        scenarioProdDAO.getByScenarioIdAndProjectIdAndUrl(response.getCount(),
                                response.getProjectId(), response.getUrl());

                aiService.createProdScenarios(prodModelList);
            }

            return saveModel;
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @GetMapping("/get/{url}")
    public ScenarioCountModel read(@PathVariable String url) {
        Optional<ScenarioCountModel> model = scenarioCountDAO.getParameterCountByUrl(url);
        if (model.isPresent()) {
            return model.get();
        } else {
            throw new RuntimeException("url not found with " + url);
        }
    }

    @GetMapping("/get")
    public List<ScenarioCountModel> readAll() {
        return scenarioCountDAO.findAll();
    }

}
