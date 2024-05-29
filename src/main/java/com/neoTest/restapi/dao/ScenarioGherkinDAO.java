package com.neoTest.restapi.dao;

import com.neoTest.restapi.model.ScenarioGherkinModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioGherkinDAO extends MongoRepository<ScenarioGherkinModel, Long> {
    List<ScenarioGherkinModel> getByUrlAndProjectIdAndScenarioId(String url, String projectId, int scenarioId);
}
