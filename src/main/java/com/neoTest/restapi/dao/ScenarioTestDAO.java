package com.neoTest.restapi.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.neoTest.restapi.model.ScenarioTestModel;

import java.util.List;

@Repository
public interface ScenarioTestDAO extends MongoRepository<ScenarioTestModel, Long> {
    List<ScenarioTestModel> getByProjectId(String projectId);
    List<ScenarioTestModel> getByScenarioId(int scenarioId);
    List<ScenarioTestModel> getByScenarioIdAndProjectId(int scenarioId, String projectId);
    List<ScenarioTestModel> getByScenarioIdAndProjectIdAndUrl(int scenarioId, String projectId, String url);
}
