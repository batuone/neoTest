package com.neoTest.restapi.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neoTest.restapi.dao.ScenarioTestDAO;
import com.neoTest.restapi.model.ScenarioTestModel;
import com.neoTest.restapi.service.SequenceGeneratorService;

@RestController
@RequestMapping("/api")
public class ScenarioController {

	@Autowired
	ScenarioTestDAO scenarioTestDAO;
	
	@Autowired
	SequenceGeneratorService seqGeneratorService;
	
	@PostMapping("/test/create")
	public ScenarioTestModel create(@RequestBody ScenarioTestModel newScenarioModel) {
		newScenarioModel.setDate(LocalDateTime.now());
		newScenarioModel.setId(seqGeneratorService.generateSequence(ScenarioTestModel.SEQUENCE_NAME));
		return scenarioTestDAO.save(newScenarioModel);
	}
	
	@GetMapping("/read")
	public List<ScenarioTestModel> read(){
		return scenarioTestDAO.findAll();
	}
	
	@GetMapping("/read/{id}")
	public ScenarioTestModel read(@PathVariable Long id) {
		Optional<ScenarioTestModel> employeeObj = scenarioTestDAO.findById(id);
		if(employeeObj.isPresent()) {
			return employeeObj.get();
		}else {
			throw new RuntimeException("Employee not found with id "+id);
		}
	}
	
	@PutMapping("/update")
	public ScenarioTestModel update(@RequestBody ScenarioTestModel modifiedEmployeeObject) {
		return scenarioTestDAO.save(modifiedEmployeeObject);
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		Optional<ScenarioTestModel> employeeObj = scenarioTestDAO.findById(id);
		if(employeeObj.isPresent()) {
			scenarioTestDAO.delete(employeeObj.get());
			return "Employee deleted with id "+id;
		}else {
			throw new RuntimeException("Employee not found for id "+id);
		}
	}
	
}
