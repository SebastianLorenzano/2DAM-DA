package com.sl2425.da.springboot_introduction.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "employee")
public class EmployeeEntity
{
    @Id
    @Column(name = "empno", nullable = false)
    private Integer id;

    @Column(name = "ename", length = 10)
    private String ename;

    @Column(name = "job", length = 9)
    private String job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deptno")
    private com.sl2425.da.springboot_introduction.Model.Entity.DeptEntity deptno;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getEname()
    {
        return ename;
    }

    public void setEname(String ename)
    {
        this.ename = ename;
    }

    public String getJob()
    {
        return job;
    }

    public void setJob(String job)
    {
        this.job = job;
    }

    public com.sl2425.da.springboot_introduction.Model.Entity.DeptEntity getDeptno()
    {
        return deptno;
    }

    public void setDeptno(com.sl2425.da.springboot_introduction.Model.Entity.DeptEntity deptno)
    {
        this.deptno = deptno;
    }

}