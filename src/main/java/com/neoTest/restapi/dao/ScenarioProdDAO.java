package com.neoTest.restapi.dao;

import com.neoTest.restapi.model.ScenarioProdModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScenarioProdDAO extends MongoRepository<ScenarioProdModel, Long> {

}
