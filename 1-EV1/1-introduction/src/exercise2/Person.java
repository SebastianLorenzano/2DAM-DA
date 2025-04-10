package exercise2;

import java.time.LocalDate;

public class Person
{
    private String name;
    private LocalDate birthDate;


    public String getName()
    {
        return name;
    }

    public void setName(String value)
    {
        name = value;
    }

    public LocalDate getBirthDate()
    {

        return birthDate;
    }

    public void setBirthDate(LocalDate value)
    {
        if (value.isAfter(LocalDate.now()))
            throw new IllegalArgumentException(Utils.BIRTHDATE_NOT_VALID);
    }
    public int getAge()
    {
        return LocalDate.now().getYear() - birthDate.getYear();
    }

}
