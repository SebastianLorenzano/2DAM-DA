package exercise2;

public class Worker extends Person
{
    private Double bruteSalary;

    public Double getBruteSalary()
    {
        return bruteSalary;
    }

    public void setBruteSalary(Double value) throws IllegalArgumentException
    {
        if (value < 0)
            throw new IllegalArgumentException(Utils.SALLARY_NOT_VALID);
        bruteSalary = value;
    }
}
