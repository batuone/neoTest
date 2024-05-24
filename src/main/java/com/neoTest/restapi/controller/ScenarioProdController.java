package com.neoTest.restapi.controller;

import com.neoTest.restapi.dao.ScenarioTestDAO;
import com.neoTest.restapi.model.ScenarioTestModel;
import com.neoTest.restapi.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/prod")
public class ScenarioProdController {

	@Autowired
	ScenarioTestDAO scenarioTestDAO;
	
	@Autowired
	SequenceGeneratorService seqGeneratorService;
	
	@PostMapping(value="/create")
	public ScenarioTestModel create(@RequestBody ScenarioTestModel testModel) {
		testModel.setDate(LocalDateTime.now());
		testModel.setId(seqGeneratorService.generateSequence(ScenarioTestModel.SEQUENCE_NAME));
		return scenarioTestDAO.save(testModel);
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
