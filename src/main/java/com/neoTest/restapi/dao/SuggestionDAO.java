package com.neoTest.restapi.dao;

import com.neoTest.restapi.model.SuggestionModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionDAO extends MongoRepository<SuggestionModel, Long> {
    List<SuggestionModel> getByProjectId(String projectId);
}
