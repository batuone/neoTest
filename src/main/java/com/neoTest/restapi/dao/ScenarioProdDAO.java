package com.neoTest.restapi.dao;

import com.neoTest.restapi.model.ScenarioProdModel;
import com.neoTest.restapi.model.ScenarioTestModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScenarioProdDAO extends MongoRepository<ScenarioProdModel, Long> {
    List<ScenarioProdModel> getByProjectId(String projectId);
    ScenarioProdModel getByScenarioId(int projectId);
}
