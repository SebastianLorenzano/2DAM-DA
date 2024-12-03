package com.jrgs2122.unit6.demo1.controllers;

import com.jrgs2122.unit6.demo1.model.dao.IDeptEntityDAO;
import com.jrgs2122.unit6.demo1.model.entities.DeptJPAEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api-rest-jrgs2122/departments")
public class DeptController {

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

    @GetMapping
    public List<DeptJPAEntity> findAllDepartments() {
        return (List<DeptJPAEntity>) deptEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeptJPAEntity> findDepartmentById(@PathVariable(value = "id") int id) {
        Optional<DeptJPAEntity> Department = deptEntityDAO.findById(id);

        if(Department.isPresent()) {
            return ResponseEntity.ok().body(Department.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public DeptJPAEntity saveDepartment(@Validated @RequestBody DeptJPAEntity Department) {
        return deptEntityDAO.save(Department);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") int id) {
        Optional<DeptJPAEntity> Department = deptEntityDAO.findById(id);
        if(Department.isPresent()) {
            deptEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}