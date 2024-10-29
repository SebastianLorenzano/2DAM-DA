package com.sl2425.da;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "employee", schema = "public", catalog = "VTInstitute_SL_2425")
public class EmployeeEntity
{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "empno")
    private int empno;
    @Basic
    @Column(name = "ename")
    private String ename;
    @Basic
    @Column(name = "job")
    private String job;
    @ManyToOne
    @JoinColumn(name = "deptno", referencedColumnName = "deptno")
    private DeptEntity Department;

    public int getEmpno()
    {
        return empno;
    }

    public void setEmpno(int empno)
    {
        this.empno = empno;
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return empno == that.empno && Objects.equals(ename, that.ename) && Objects.equals(job, that.job);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(empno, ename, job);
    }

    public DeptEntity getDepartment()
    {
        return Department;
    }

    public void setDepartment(DeptEntity department)
    {
        Department = department;
    }
}
