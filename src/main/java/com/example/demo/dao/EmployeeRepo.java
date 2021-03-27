package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
	
	@Query
	(value = "from Employee where firstname=:fname and lastname=:lname")
	Employee findByName(String fname, String lname);

}
