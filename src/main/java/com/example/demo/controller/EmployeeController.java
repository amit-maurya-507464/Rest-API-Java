package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;
import com.example.demo.service.EmployeeService;

@RestController
public class EmployeeController {
	
	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService service;
	
	@Value("${message.notExist}")
	private String notExist;
	
	@Value("${message.invalid}")
	private String invalid;
	
	@Value("${message.exist}")
	private String exist;
	
	@Value("${message.added}")
	private String added;
	
	@Value("${message.deleted}")
	private String deleted;
	
	@Value("${message.updated}")
	private String updated;
	
	@GetMapping("/employees")
	public ResponseEntity<?> getEmployees() {
		logger.info("Resust for all employees data");
		List<Employee> employees = service.listAll();
		logger.info("response sent ::"+employees);
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
	
	
	@GetMapping("/employee/{empId}")
	public ResponseEntity<?> getEmployee(@PathVariable String empId) {
		logger.info("Request for employee with id :: "+empId);
		if(service.isNumeric(empId)) {
			int id = Integer.parseInt(empId);
			if(service.isExist(id)) {
		        Employee employee = service.get(id);
		        logger.info("Response sent for Id = {} :: {}", id, employee);
		        return new ResponseEntity<>(employee, HttpStatus.OK);
		    }
		    else {
		    	logger.info(notExist);
		        return new ResponseEntity<>(notExist,HttpStatus.OK);
		    }
			}
			else {
				logger.info(invalid);
				return new ResponseEntity<>(invalid, HttpStatus.OK);
			}      
	}
	
	
	@PostMapping("/employee")
	public ResponseEntity<?> save(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            logger.error("Error in requst:: "+errors);
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }
		logger.info("Request to Save employee :: "+employee);
		Employee emp = service.exist(employee);
		if(emp==null) {
			service.save(employee);
			logger.info(added);
			return new ResponseEntity<>(added, HttpStatus.OK);
		}
		else {
			logger.info(exist);
			return new ResponseEntity<>(exist, HttpStatus.OK);
		}
	}
	
	
	@PutMapping("/employee/{empId}")
	public ResponseEntity<?> update(@Valid @RequestBody Employee employee, BindingResult bindingResult, @PathVariable String empId) {
		logger.info("Request to update employee data with Id = {} and data = {} ",empId,employee );
		if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            logger.error("Error in requst:: "+errors);
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }
		if(service.isNumeric(empId)) {
			int id = Integer.parseInt(empId);
			if(service.isExist(id)) {
		        Employee emp = service.get(id);
		        service.update(employee, emp);
		        logger.info(updated);
		        return new ResponseEntity<>(updated, HttpStatus.OK);
		    }
		    else {
		    	logger.info(notExist);
		        return new ResponseEntity<>(notExist, HttpStatus.OK);
		    }
			}
			else {
				logger.info(invalid);
				return new ResponseEntity<>(invalid, HttpStatus.OK);
			}      
	}
	
	
	@DeleteMapping("/employee/{empId}")
	public ResponseEntity<?> delete(@PathVariable String empId) {
		logger.info("Request to delete data for employee with id :: "+empId);
		if(service.isNumeric(empId)) {
			int id = Integer.parseInt(empId);
			if(service.isExist(id)) {
		        service.delete(id);
		        logger.info(deleted);
		        return new ResponseEntity<>(deleted, HttpStatus.OK);
		        }
		        else {
		        	logger.info(notExist);
		        	return new ResponseEntity<>(notExist, HttpStatus.OK);
		        }
			}
			else {
				logger.info(invalid);
				return new ResponseEntity<>(invalid, HttpStatus.OK);
			}
	}
	
}
