package com.sl2425.da.demo1.controllers;


import com.sl2425.da.demo1.model.dao.IDeptEntityDAO;
import com.sl2425.da.demo1.model.dto.EmployeeDTO;
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
@RequestMapping("/employees")
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
    public ResponseEntity<EmployeeEntity> saveEmployee(@Validated @RequestBody EmployeeEntity Employee) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(Employee.getId());
        if (employee.isPresent())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(employeeEntityDAO.save(Employee));
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@Validated @RequestBody EmployeeEntity Employee) {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(Employee.getId());
        if (!employee.isPresent())
            return ResponseEntity.badRequest().build();
        employeeEntityDAO.save(Employee);
        return ResponseEntity.ok().body("Updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable(value = "id") int id)
    {
        Optional<EmployeeEntity> Employeexml = employeeEntityDAO.findById(id);
        if(Employeexml.isPresent())
        {
            employeeEntityDAO.deleteById(id);
            return ResponseEntity.ok().body("Deleted");
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("dto/{id}")
    public ResponseEntity<EmployeeDTO> searchEmployeeDTOById(@PathVariable(value = "id") int id)
    {
        Optional<EmployeeEntity> employee = employeeEntityDAO.findById(id);

        if (employee.isPresent())
        {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setEmpno(employee.get().getId());
            employeeDTO.setEname(employee.get().getEname());
            employeeDTO.setJob(employee.get().getJob());
            employeeDTO.setDepno(employee.get().getDeptno().getId());
            employeeDTO.setDname(employee.get().getDeptno().getDname());
            employeeDTO.setDloc(employee.get().getDeptno().getLoc());
            return ResponseEntity.ok().body(employeeDTO);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
}