package com.sl2425.da.demo1.model.dto;

public class EmployeeDTO
{
    private int empno;
    private String ename;
    private String job;
    private int depno;
    private String dname;
    private String dloc;

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

    public int getDepno()
    {
        return depno;
    }

    public void setDepno(int depno)
    {
        this.depno = depno;
    }

    public String getDname()
    {
        return dname;
    }

    public void setDname(String dname)
    {
        this.dname = dname;
    }

    public String getDloc()
    {
        return dloc;
    }

    public void setDloc(String dloc)
    {
        this.dloc = dloc;
    }
}