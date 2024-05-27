package com.neoTest.restapi.dao;

import com.neoTest.restapi.model.ScenarioCountModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScenarioCountDAO extends MongoRepository<ScenarioCountModel, Long> {
    ScenarioCountModel getByUrl(String url);
    Optional<ScenarioCountModel> getParameterCountByUrl(String url);
}
