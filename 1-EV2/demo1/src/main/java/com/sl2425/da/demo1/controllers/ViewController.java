package com.sl2425.da.demo1.controllers;

import com.sl2425.da.demo1.model.dao.IDeptEntityDAO;
import com.sl2425.da.demo1.model.entities.DeptEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ViewController
{
    @Autowired
    private IDeptEntityDAO deptDAO;

    @GetMapping("/web")
    public String index()
    {
        return "index";
    }

    @GetMapping("/web/departments")
    public String showDepartments(Model model)
    {
        List<DeptEntity> departments = (List<DeptEntity>) deptDAO.findAll();
        model.addAttribute("departments", departments);
        return "departments";
    }
}
