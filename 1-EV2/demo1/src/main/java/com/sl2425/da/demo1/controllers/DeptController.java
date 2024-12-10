package com.sl2425.da.demo1.controllers;

import com.sl2425.da.demo1.model.dao.IDeptEntityDAO;
import com.sl2425.da.demo1.model.entities.DeptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/departments")
public class DeptController {

    @Autowired
    private IDeptEntityDAO deptEntityDAO;

    @GetMapping
    public List<DeptEntity> findAllDepartments() {
        return (List<DeptEntity>) deptEntityDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeptEntity> findDepartmentById(@PathVariable(value = "id") int id) {
        Optional<DeptEntity> Department = deptEntityDAO.findById(id);

        if(Department.isPresent()) {
            return ResponseEntity.ok().body(Department.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> saveDepartment(@Validated @RequestBody DeptEntity Department)
    {
        Optional<DeptEntity> dept = deptEntityDAO.findById(Department.getId());
        if (dept.isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(deptEntityDAO.save(Department));
    }

    @PutMapping
    public ResponseEntity<?> updateDepartment(@Validated @RequestBody DeptEntity Department)
    {
        Optional<DeptEntity> dept = deptEntityDAO.findById(Department.getId());
        if (!dept.isPresent())
            return ResponseEntity.badRequest().build();
        deptEntityDAO.save(Department);
        return ResponseEntity.ok().body("Updated");
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable(value = "id") int id) {
        Optional<DeptEntity> Department = deptEntityDAO.findById(id);
        if(Department.isPresent()) {
            deptEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}