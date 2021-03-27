package com.example.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Employee;
import com.example.demo.dao.EmployeeRepo;

@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepo repo;
	
	public List<Employee> listAll() {
        return repo.findAll();
    }
     
    public void save(Employee employee) {
        repo.save(employee);
    }
     
    public Employee get(Integer id) {
        return repo.findById(id).get();
    }
     
    public void delete(Integer id) {
        repo.deleteById(id);
    }
    
    public boolean isExist(int id) {
    	return repo.existsById(id);
    }
    
    public void update(Employee employee, Employee emp) {
    	emp.setFirstname(employee.getFirstname());
        emp.setLastname(employee.getLastname());
        emp.setEmail(employee.getEmail());
        emp.setSalary(employee.getSalary());
        emp.setDepartment(employee.getDepartment());
        repo.save(emp);
    }
    
    public Employee exist(Employee employee) {
    	Employee emp = repo.findByName(employee.getFirstname(), employee.getLastname());
    	return emp;
    }
    
    public boolean isNumeric(String str) {
		  return str.matches("\\d+");
		}
}
