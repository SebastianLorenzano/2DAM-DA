package com.jrgs2122.unit6.demo1.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee", schema = "public", catalog = "Employees")
public class EmployeeJPAEntity {
    private int empno;
    private String ename;
    private String job;
    private DeptJPAEntity department;

    @Id
    @Column(name = "empno", nullable = false)
    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    @Basic
    @Column(name = "ename", nullable = true, length = 10)
    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    @Basic
    @Column(name = "job", nullable = true, length = 9)
    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeJPAEntity that = (EmployeeJPAEntity) o;
        return empno == that.empno && Objects.equals(ename, that.ename) && Objects.equals(job, that.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empno, ename, job);
    }

    @ManyToOne
    @JoinColumn(name = "deptno", referencedColumnName = "deptno")
    //@JsonIgnoreProperties("employees")
    public DeptJPAEntity getDepartment() {
        return department;
    }

    public void setDepartment(DeptJPAEntity department) {
        this.department = department;
    }
}
