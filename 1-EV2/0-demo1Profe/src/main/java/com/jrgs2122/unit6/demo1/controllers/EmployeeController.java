package com.jrgs2122.unit6.demo1.controllers;

import com.jrgs2122.unit6.demo1.model.dao.IEmployeeEntityDAO;
import com.jrgs2122.unit6.demo1.model.entities.EmployeeJPAEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api-rest-jrgs2122/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    @GetMapping
    public List<EmployeeJPAEntity> findAllEmployees() {
        return (List<EmployeeJPAEntity>) employeeEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeJPAEntity> findEmployeesById(@PathVariable(value = "id") int id) {
        Optional<EmployeeJPAEntity> Employee = employeeEntityDAO.findById(id);

        if(Employee.isPresent()) {
            return ResponseEntity.ok().body(Employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public EmployeeJPAEntity saveEmployee(@Validated @RequestBody EmployeeJPAEntity Employeexml) {
        return employeeEntityDAO.save(Employeexml);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int id) {
        Optional<EmployeeJPAEntity> Employeexml = employeeEntityDAO.findById(id);
        if(Employeexml.isPresent()) {
            employeeEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}