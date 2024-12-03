package com.sl2425.da.demo1.controllers;


import com.sl2425.da.demo1.model.dao.IDeptEntityDAO;
import com.sl2425.da.demo1.model.dao.IEmployeeEntityDAO;
import com.sl2425.da.demo1.model.entities.EmployeeEntity;
import com.sl2425.da.demo1.model.entities.DeptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api-rest-/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;

    @GetMapping
    public List<EmployeeEntity> findAllEmployees() {
        return (List<EmployeeEntity>) employeeEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> findEmployeesById(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> Employee = employeeEntityDAO.findById(id);

        if(Employee.isPresent()) {
            return ResponseEntity.ok().body(Employee.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public EmployeeEntity saveEmployee(@Validated @RequestBody EmployeeEntity Employeexml) {
        return employeeEntityDAO.save(Employeexml);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int id) {
        Optional<EmployeeEntity> Employeexml = employeeEntityDAO.findById(id);
        if(Employeexml.isPresent()) {
            employeeEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}