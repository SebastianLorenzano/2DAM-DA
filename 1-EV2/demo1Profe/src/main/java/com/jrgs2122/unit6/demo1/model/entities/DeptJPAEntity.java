package com.jrgs2122.unit6.demo1.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dept", schema = "public", catalog = "Employees")
public class DeptJPAEntity {
    private int deptno;
    private String dname;
    private String loc;
    private List<EmployeeJPAEntity> employees;

    @Id
    @Column(name = "deptno", nullable = false)
    public int getDeptno() {
        return deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    @Basic
    @Column(name = "dname", nullable = true, length = 14)
    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    @Basic
    @Column(name = "loc", nullable = true, length = 13)
    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeptJPAEntity that = (DeptJPAEntity) o;
        return deptno == that.deptno && Objects.equals(dname, that.dname) && Objects.equals(loc, that.loc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deptno, dname, loc);
    }

    @OneToMany(mappedBy = "department")
    //@JsonIgnoreProperties("department")
    public List<EmployeeJPAEntity> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeJPAEntity> employees) {
        this.employees = employees;
    }
}
