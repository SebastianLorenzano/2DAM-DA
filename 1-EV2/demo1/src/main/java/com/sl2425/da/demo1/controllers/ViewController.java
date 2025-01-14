package com.sl2425.da.demo1.controllers;

import com.sl2425.da.demo1.model.dao.IDeptEntityDAO;
import com.sl2425.da.demo1.model.dao.IEmployeeEntityDAO;
import com.sl2425.da.demo1.model.entities.DeptEntity;
import com.sl2425.da.demo1.model.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ViewController
{
    @Autowired
    private IDeptEntityDAO deptDAO;
    @Autowired
    private IEmployeeEntityDAO employeeDAO;

    @GetMapping({"/web/", "/web/index", "/web/index.html"})
    public String index()
    {
        return "index";
    }

    @GetMapping({"/web/departments", "/web/departments.html"})
    public String showDepartments(Model model)
    {
        List<DeptEntity> departments = (List<DeptEntity>) deptDAO.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }

    @GetMapping("/web/employees")
    public String showEmployees(Model model)
    {
        List<EmployeeEntity> employees = (List<EmployeeEntity>) employeeDAO.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/web/departments-save")
    public String showDepartmentSave(Model model)
    {
        model.addAttribute("department", new DeptEntity());
        return "departments-save";
    }

    @PostMapping("/web/departments-save")
    public String saveDepartment(DeptEntity department, Model model)
    {
        if (!deptDAO.existsById(department.getId()))
            deptDAO.save(department);

        model.addAttribute("department", department);
        return "departments-save";
    }

    @GetMapping("/web/employees-save")
    public String showEmployeesSave(Model model)
    {
        model.addAttribute("employee", new EmployeeEntity());
        model.addAttribute("departments", deptDAO.findAll());
        return "employees-save";
    }

    @PostMapping("/web/employees-save")
    public String saveEmployee(EmployeeEntity employeeEntity, Model model)
    {
        if (!employeeDAO.existsById(employeeEntity.getId()) &&
            deptDAO.existsById(employeeEntity.getDeptno().getId()))
            employeeDAO.save(employeeEntity);
        else
        {
            model.addAttribute("employee", employeeEntity);
            model.addAttribute("departments", deptDAO.findAll());
            model.addAttribute("error", "Employee could not be saved. Check the input.");
        }
        return "employees-save";
    }
}
