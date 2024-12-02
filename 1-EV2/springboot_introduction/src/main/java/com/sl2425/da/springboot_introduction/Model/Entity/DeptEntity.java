package com.sl2425.da.springboot_introduction.Model.Entity;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "dept")
public class DeptEntity
{
    @Id
    @Column(name = "deptno", nullable = false)
    private Integer id;

    @Column(name = "dname", length = 14)
    private String dname;

    @Column(name = "loc", length = 13)
    private String loc;

    @OneToMany(mappedBy = "deptno")
    private Set<com.sl2425.da.springboot_introduction.Model.Entity.EmployeeEntity> employees = new LinkedHashSet<>();

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDname()
    {
        return dname;
    }

    public void setDname(String dname)
    {
        this.dname = dname;
    }

    public String getLoc()
    {
        return loc;
    }

    public void setLoc(String loc)
    {
        this.loc = loc;
    }

    public Set<com.sl2425.da.springboot_introduction.Model.Entity.EmployeeEntity> getEmployees()
    {
        return employees;
    }

    public void setEmployees(Set<com.sl2425.da.springboot_introduction.Model.Entity.EmployeeEntity> employees)
    {
        this.employees = employees;
    }

}