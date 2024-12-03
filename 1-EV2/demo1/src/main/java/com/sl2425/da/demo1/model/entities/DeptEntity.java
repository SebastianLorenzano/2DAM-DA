package com.sl2425.da.demo1.model.entities;

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

    @OneToMany(mappedBy = "deptno")

    private Set<EmployeeEntity> employees = new LinkedHashSet<>();
    public Set<EmployeeEntity> getEmployees()
    {
        return employees;
    }

    public void setEmployees(Set<EmployeeEntity> employees)
    {
        this.employees = employees;
    }


}