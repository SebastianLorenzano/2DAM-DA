package com.sl2425.da.demo1.services;

import com.sl2425.da.demo1.model.dao.IEmployeeEntityDAO;
import com.sl2425.da.demo1.model.dto.EmployeeDTO;
import com.sl2425.da.demo1.model.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

public class EmployeeServices
{
    @Autowired
    private IEmployeeEntityDAO employeeEntityDAO;



    public ResponseEntity<EmployeeDTO> searchEmployeeDTOById(int id)
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
